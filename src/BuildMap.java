import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sailpoint.connector.DelimitedFileConnector;
import sailpoint.connector.JDBCConnector;
import sailpoint.object.Filter;
import sailpoint.object.Identity;
import sailpoint.object.QueryOptions;
import sailpoint.object.RoleAssignment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BuildMap {
    private static final Logger log = LoggerFactory.getLogger(BuildMap.class);

    public static void main(String[] args){
        Map map = DelimitedFileConnector.defaultBuildMap();
        if (map==null){
            return null;
        }
            String projectUsername = (String) map.get("projectUsername");
            String status = (String) map.get("accountStatus");
            String licenseTier = (String) map.get("licenseTier");
            if (Util.isNotNullOrEmpty(projectUsername)){
                projectUsername = projectUsername.toUpperCase();
            }map.put("projectUsername",projectUsername);
            if (Util.isNotNullOrEmpty(status)){
                if ("Enabled".equalsIgnoreCase(status)){
                    map.put("accountStatus","Active");
                } else map.put("accountStatus","Inactive");
            }
            if (Util.isNotNullOrEmpty(licenseTier)){
                if ("Enterprise".equalsIgnoreCase(licenseTier)){
                    map.put("licenseTier","ENT");
                } else map.put("licenseTier","STD");
                }return map;
 if (licenseTier!=null){
 }
 //Active Directory Correlation rule
        log.info("Starting Correlation Rule");
        Map map1 = new HashMap();
        Iterator iterator1 = null;
        if (account==null){
            return null;
        }        String mail = account.getStringAttribute("mail");
        String sAMAccountName = account.getStringAttribute("sAMAccountName");
        log.debug("Account Email = " + mail);
        if (Util.isNotNullOrEmpty(mail)){
        try {
            QueryOptions queryOptions  = new QueryOptions();
            queryOptions.addFilter(Filter.eq("email",mail));
            iterator1 = context.search(Identity.class,queryOptions);
            if (iterator1 !=null && iterator1.hasNext()){
                Identity identity1 = (Identity) iterator1.next();
                if (iterator1.hasNext()){
                    log.error("Duplicate email found for " + mail);
                    return null;
                }
                map1.put("identity",identity1);
                return map1;
            }
        }catch (GeneralException e){
            log.error("Correlation rule failed for Email");
        }finally {
            Util.flushIterator(iterator1);
        }
    }
        if (Util.isNotNullOrEmpty(sAMAccountName)){
            try {
                Identity identity1 = context.getObjectByName(Identity.class,sAMAccountName);
                if (identity1 !=null){
                    map1.put("identity",identity1);
                    return map1;
                }
            }catch (GeneralException e){
                log.error("Username Correlation Failed");
            }
        }
        log.info("Starting Trakk App--Correlation Rule");
        Map resultMap = new HashMap();
        Iterator iterator = null;
        if (account==null){
            return;
        }
String employeeId = account.getStringAttribute("id");
        log.debug("EmployeeID = "+ employeeId);
       if (Util.isNotNullOrEmpty(employeeId)){
           try {
               QueryOptions queryOptions = new QueryOptions();
               queryOptions.add(Filter.eq("displayName",employeeId));
               iterator = context.getObjectByName(Identity.class,queryOptions);
               if (iterator!=null && iterator.hasNext()){
                  Identity identity = (Identity) iterator.next();
                  resultMap.put("identity",identity);
                  return resultMap;

               }
           }catch (GeneralException e){
               log.error("EmployeeID Correlation Failed " + e);
           }finally {
               Util.flushIterator(iterator);
           }
       }
//Prism Correlation Rule
        Map resultMap = new HashMap();
       Iterator iterator = null;
       log.info("Starting PRISM Correlation");
       String login  = account.getAttribute("login");
       if (Util.isNotNullOrEmpty(login)){
           try {
               QueryOptions queryOptions = new QueryOptions();
               queryOptions.add(Filter.eq("",login));
               iterator = context.search(Identity.class,queryOptions);
               if (iterator!=null && iterator.hasNext()){
                   Identity identity = (Identity) iterator.next();
                   resultMap.put("identity",identity);
                   return resultMap;
               }
           } catch (Exception e) {
              log.error("Username correlation failed" + e);
           }finally {
               Util.flushIterator(iterator);
           }
       }String firstName = account.getStringAttribute("first");
       if (log.isDebugEnabled()) {
           log.debug("firstName = " + firstName);
       }
        String lastName = account.getStringAttribute("last");
        if (log.isDebugEnabled()){
        log.debug("Last Name = " + lastName);}
        if (firstName==null || lastName == null){
            log.warn("Skipping Correlation: Missing firstName (" + firstName + ") or lastName ("+ lastName + ")");
            return null;
        }String correlationKey  = firstName.trim() + "." + lastName.trim();
        resultMap.put("identityAttributeName","displayName");
        resultMap.put("identityAttributeValue",correlationKey);
        log.info("Correlation Rule Finished");
        return resultMap;

//PRISM BuildMap Rule
Map map  = JDBCConnector.buildMapFromResultSet(result);
String active  = map.get("status");
if (active.equalsIgnoreCase("A")){
    map.put("IIQDisabled",false);
}else map.put("IIQDisabled",true);
String locked = map.get("locked");
if (locked.equalsIgnoreCase("N")){
    map.put("IIQLocked",false);
}else map.put("IIQLocked",true);
    return map;

    String name = "Shivam";
    String reversed = new StringBuilder(name).reverse().toString();
    }

}
