import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.entity.AuditStatus;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.List;
import java.util.Map;

public class SolrUtil {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private SolrTemplate solrTemplate;

    public void importItemData(){
        ItemQuery itemQuery = new ItemQuery();
        ItemQuery.Criteria criteria = itemQuery.createCriteria();
        criteria.andStatusEqualTo(AuditStatus.Audit);
        List<Item> items = itemDao.selectByExample(itemQuery);
        for (Item item:items) {
            Map<String,String> specMap = JSON.parseObject(item.getSpec(),Map.class);
            item.setSpecMap(specMap);
        }
        solrTemplate.saveBeans(items);
        solrTemplate.commit();
    }
}
