import sailpoint.object.Filter;
import sailpoint.object.Identity;
import sailpoint.object.QueryOptions;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class git {
    public static  void main(String[] args){
        // This line was added by Developer B on the cloud.
        Map resultMap = new HashMap();
        Iterator iterator12 = null;
        String contractorEmail = (String) account.getAttribute("contractorEmail");//Reading Contractor Email
        if (Util.isNullOrEmpty(contractorEmail)){
            return null;
        }
        try {
            QueryOptions queryOptions7 = new QueryOptions();
            queryOptions7.add(Filter.eq("contractorEmail",contractorEmail));
            iterator12 = context.search(Identity.class,queryOptions7);
            if (iterator12.hasNext()){
                identity2 = (Identity) iterator12.next();
                resultMap.put("identity",identity2);
                return resultMap;
            }
        }catch (GeneralException e){
            log.error("Correlation Rule Failed using Contractor Email");
        }finally {
            Util.flushIterator(iterator12);
        }

    }
}
