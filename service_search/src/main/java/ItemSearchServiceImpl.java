import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.service.ItemSearchService;
import cn.itcast.core.util.Constants;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.*;

public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {
        // 根据参数到 solr 中 分页 高亮 过滤 排序查询
        Map<String, Object> resultMap = highlightSearch(searchMap);
        // 根据查询参数到solr 获取对应的分类结果集 分类重复，需要去重
        List<String> groupCatagoryList = findGroupCatagoryList(searchMap);
        resultMap.put("catagoryList",groupCatagoryList);
        // 判断参数中是否带有 分类
        String catagory = String.valueOf(searchMap.get("catagory"));
        if (catagory!=null&&!"".equals(catagory)){
            Map specListAndBrandList = findSpecListAndBrandList(catagory);
            resultMap.putAll(specListAndBrandList);
        }else {
            Map specListAndBrandList = findSpecListAndBrandList(groupCatagoryList.get(0));
            resultMap.putAll(specListAndBrandList);
        }
        return resultMap;
    }

    private Map findSpecListAndBrandList(String catagory) {
        // 根据分类信息到redis 查询对应的模板信息
        Long templateId = (Long) redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).get(catagory);
        // 根据 模板id 到redis 查询对应的brand信息
        List<Map> bandList = (List<Map>) redisTemplate.boundHashOps(Constants.BRAND_LIST_REDIS).get(templateId);
        // 根据 模板id 到redis 查询对应的spec信息
        List<Map> specList = (List<Map>) redisTemplate.boundHashOps(Constants.SPEC_LIST_REDIS).get(templateId);
        // 查询的结果封装到 map中
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("brandList",bandList);
        resultMap.put("specList",specList);
        return resultMap;
    }

    private List<String> findGroupCatagoryList(Map searchMap) {
        List<String> resultList = new ArrayList<>();
        String keyword = String.valueOf(searchMap.get("keywords"));
        if (keyword!=null){
            keyword.replaceAll(" ","");
        }
        // 创建 solr 的查询对象
        Query query = new SimpleQuery();
        Criteria criteria = new Criteria("item_keywords").is(keyword);
        query.addCriteria(criteria);
        // 创建分组查询对象
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        // 查询分类集合
        GroupPage<Item> items = solrTemplate.queryForGroupPage(query, Item.class);
        GroupResult<Item> item_category = items.getGroupResult("item_category");
        Page<GroupEntry<Item>> groupEntries = item_category.getGroupEntries();
        for (GroupEntry<Item> groupEntry:groupEntries) {
            String groupValue = groupEntry.getGroupValue();
            resultList.add(groupValue);
        }
        return resultList;
    }

    public Map<String, Object> highlightSearch(Map searchMap) {
        String keywords = String.valueOf(searchMap.get("keywords"));
        if (keywords != null) {
            keywords.replaceAll(" ", "");
        }
        // 当前页
        Integer page = Integer.parseInt(String.valueOf(searchMap.get("page")));
        // 每页多少条数据
        Integer pageSize = Integer.parseInt(String.valueOf(searchMap.get("pageSize")));
        // 获取页面点击的分类过滤条件
        String category = String.valueOf(searchMap.get("category"));
        // 获取页面点击的品牌过滤条件
        String brand = String.valueOf(searchMap.get("brand"));
        // 获取页面点击的规格过滤条件
        String spec = String.valueOf(searchMap.get("spec"));
        // 获取页面点击的价格区间过滤条件
        String price = String.valueOf(searchMap.get("price"));
        // 获取页面点击的排序的域
        String sortField = String.valueOf(searchMap.get("sortField"));
        // 获取页面点击的排序的方式
        String sortType = String.valueOf(searchMap.get("sortType"));
        // 创建查询对象
        HighlightQuery highlightQuery = new SimpleHighlightQuery();
        // 创建条件查询对象
        Criteria criteria = new Criteria("item_keywords").is("keywords");
        highlightQuery.addCriteria(criteria);
        // 计算从第几条查询
        if (page == null && page <= 0){
            page=1;
        }
        Integer start = (page-1)*pageSize;
        highlightQuery.setOffset(start);
        // 设置查询多少数据
        highlightQuery.setRows(pageSize);
        // 创建高亮选项
        HighlightOptions highlightOptions = new HighlightOptions();
        // 创建设置高亮显示的域
        highlightOptions.addField("item_title");
        // 设置 高亮前后缀
        highlightOptions.setSimplePrefix("<em style=\\\"color:red\\\">");
        highlightOptions.setSimplePrefix("</em>");
        // 添加到查询对象中
        highlightQuery.setHighlightOptions(highlightOptions);
        // 根据分类查询
        if (category!=null&&!"".equals(category)){
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(category);
            filterQuery.addCriteria(filterCriteria);
            highlightQuery.addFilterQuery(filterQuery);
        }
        // 根据品牌查询
        if(brand!=null&&!"".equals(brand)){
            FilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_brand").is(brand);
            filterQuery.addCriteria(filterCriteria);
            highlightQuery.addFilterQuery(filterQuery);
        }
        // 根据规格查询
        if(spec!=null&&!"".equals(spec)){
            Map<String,String> specMap = JSON.parseObject(spec,Map.class);
            if(specMap!=null&&specMap.size()>0){
                Set<Map.Entry<String,Object>> set = searchMap.entrySet();
                for (Map.Entry<String,Object> s:set) {
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_spec_"+s.getKey()).is(s.getValue());
                    filterQuery.addCriteria(filterCriteria);
                    highlightQuery.addFilterQuery(filterQuery);
                }
            }
        }
        // 根据价格查询
        if(price!=null && !"".equals(price)){
            String[] prices = price.split("_");
            if(prices!=null&&prices.length==2){
                if (!"0".equals(prices[0])){
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
                    filterQuery.addCriteria(filterCriteria);
                    highlightQuery.addFilterQuery(filterQuery);
                }
                if (!"*".equals(prices[1])){
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
                    filterQuery.addCriteria(filterCriteria);
                    highlightQuery.addFilterQuery(filterQuery);
                }
            }
        }
        // 根据排序查询
        if(sortField!=null&&sortType!=null&&!"".equals(sortField)&&!"".equals(sortType)){
            if("ACS".equals(sortType)){
                Sort sort = new Sort(Sort.Direction.ASC,"item_"+sortField);
                highlightQuery.addSort(sort);
            }
            if ("DECS".equals(sortType)){
                Sort sort = new Sort(Sort.Direction.DESC,"item_"+sortField);
                highlightQuery.addSort(sort);
            }
        }
        // 返回查询结果
        HighlightPage<Item> items = solrTemplate.queryForHighlightPage(highlightQuery, Item.class);
        List<HighlightEntry<Item>> highlighted = items.getHighlighted();
        List<Item> itemList = new ArrayList<>();
        // 遍历高亮集合
        for (HighlightEntry<Item> highlightEntry:highlighted) {
            Item item = highlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            if(highlights!=null&&highlights.size()>0){
                List<String> snipplets = highlights.get(0).getSnipplets();
                if(snipplets!=null&&snipplets.size()>0){
                    String title = snipplets.get(0);
                    item.setTitle(title);
                }
            }
            itemList.add(item);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", itemList);
        resultMap.put("totalPages",items.getTotalPages() );
        resultMap.put("total", items.getTotalElements());
        return resultMap;
    }
}
