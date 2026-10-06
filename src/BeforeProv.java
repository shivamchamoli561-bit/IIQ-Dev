import com.twilio.rest.api.v2010.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ehcache.core.spi.store.Store;
import org.yaml.snakeyaml.events.Event;
import sailpoint.connector.DelimitedFileConnector;
import sailpoint.connector.JDBCConnector;
import sailpoint.object.*;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;
import sailpoint.web.accessrequest.AccessRequest;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


public class BeforeProv {
    public static void main(String[] args) {
        Log log = LogFactory.getLog("Custom.BeforeProvionsing.Rule");
        Application application = new Application();
        ProvisioningPlan plan = new ProvisioningPlan();
        Identity identity = plan.getIdentity();
       identity.getFirstname().
        if (plan == null || identity == null) {
            log.warn("Plan or Identity Object is null--End Rule");
            return;
        }
        String employeeType = String.valueOf(identity.getAttribute("EmployeeType"));
        log.debug("Employee Type= " + employeeType);
        if (!"Contractor".equalsIgnoreCase(employeeType)) {
            log.warn("Identity is not a Contractor--End Rule");
            return;
        }
        Identity manager = identity.getManager();
        if (manager == null) {
            log.warn("Manager not Found--End Rule");
            return;
        }
        String department = String.valueOf(manager.getAttribute("Department"));
        if (!"HR".equalsIgnoreCase(department)) {
            log.warn("HR Department not found--End Rule");
            return;
        }
        String groupName = (String) args.get("GroupName");
        if (groupName == null) {
            log.warn("GroupName is not configured--End Rule");
            return;
        }
        if (identity.getLinks() == null) {
            log.warn("Identity has no Links");
            return;
        }
        boolean hasAd = false;
        for (Link link : identity.getLinks()) {
            if (link == null) {
                log.debug("Skipping null links");
                continue;
            }
            if ("AD".equalsIgnoreCase(link.getApplicationName())) {
                hasAd = true;
                break;
            }
        }
        if (!hasAd) {
            log.warn("End Rule if Identity does not have an existing AD");
            return;
        }
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()) {
            if (ar == null) {
                log.debug("Skipping null values");
                continue;
            }
            if ("AWS".equalsIgnoreCase(ar.getApplicationName())
                    && ar.getOperation() == ProvisioningPlan.AccountRequest.Operation.Create) {
                ProvisioningPlan.AttributeRequest group = new ProvisioningPlan.AttributeRequest(
                        "Group", ProvisioningPlan.Operation.Add, groupName
                );
                ar.add(group);
                log.info("Group Added");
            }
        }
        QueryOptions qo = new QueryOptions();
        qo.addFilter(Filter.like("email",
                "@gmail.com",
                Filter.MatchMode.END));
        Iterator iterator = context.search(Identity.class, qo);
        while (iterator.hasNext()) {
            Identity identity1 = (Identity) iterator.next();
            log.info(identity1.getName());
        }
        QueryOptions qo1 = new QueryOptions();
        qo1.addFilter(Filter.isnull("manager"));
        Iterator iterator1 = context.search(Identity.class, qo1);
        while (iterator1.hasNext()) {
            Identity identity2 = (Identity) iterator1.next();
            log.info(identity2.getName());
        }
        QueryOptions qo2 = new QueryOptions();
        qo2.add(Filter.eq("employeeType", "Contractor"));
        qo2.setResultLimit(10);
        Iterator iterator2 = context.search(Identity.class, qo2);
        while (iterator.hasNext()) {
            Identity identity2 = (Identity) iterator2.next();
            log.info(identity2.getName());
        }
        QueryOptions qo3 = new QueryOptions();
        qo3.add(Filter.and(
                Filter.eq("department", "HR"),
                Filter.eq("employeeType", "Employee")
        ));
        qo3.addOrdering("lastname", true);
        Iterator iterator3 = context.search(Identity.class, qo3);
        while (iterator3.hasNext()) {
            Identity identity3 = (Identity) iterator3.next();
            log.info(identity3.getName());
        }
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addFilter(Filter.and(
                Filter.eq("department", "IT"),
                Filter.eq("country", "UK"),
                Filter.like("email", "company.com", Filter.MatchMode.ANYWHERE),
                Filter.not(Filter.isnull("manager"))
        ));
        qo3.setResultLimit(25);
        Iterator iterator4 = context.search(Identity.class, qo3);
        while (iterator4.hasNext()) {
            Identity identity3 = (Identity) iterator4.next();
            log.debug(identity3.getName());
        }
        Identity manager5 = identity.getManager();
        log.debug(manager5.getName());
        if (identity.getLinks() == null) {
            return;
        }
        for (Link link : manager.getLinks()) {
            if (link == null) {
                continue;
            }
            log.debug(link.getApplicationName());
            log.debug(link.getNativeIdentity());
        }
        if (identity.getLinks() == null) {
            return;
        }
        for (Link link : manager.getLinks()) {
            if (link == null) {
                continue;
            }
            Application app = link.getApplication();
            if (app == null) {
                continue;
            }
            Identity owner = app.getOwner();
            if (owner == null) {
                continue;
            }
            log.debug(owner.getName());
            log.debug(owner.getEmail());
            String dept = String.valueOf(owner.getAttribute("department"));
            log.debug(dept);
        }
        if (identity.getLinks() == null) {
            return;
        }
        for (Link link : identity.getLinks()) {
            if (link == null) {
                continue;
            }
            if (!"AD".equalsIgnoreCase(link.getApplicationName())) {
                continue;
            }
            Application app = link.getApplication();
            if (app == null) {
                continue;
            }
            Identity owner = app.getOwner();
            log.debug(owner.getName());
            QueryOptions queryOptions1 = new QueryOptions();
            queryOptions1.addFilter(Filter.eq("manager", owner));
            Iterator iterator5 = context.search(Identity.class, qo);
            while (iterator5.hasNext()) {
                Identity id = (Identity) iterator5.next();
                log.info(id.getName());
            }
        }
        QueryOptions queryOptions1 = new QueryOptions();
        queryOptions1.addFilter(Filter.eq("application.name", "AD"));
        Iterator iterator6 = context.search(ManagedAttribute.class, queryOptions1);
        while (iterator6.hasNext()) {
            ManagedAttribute managedAttribute = (ManagedAttribute) iterator6.next();
            log.info(managedAttribute.getValue());
            log.info(managedAttribute.getDisplayName());
            log.info(managedAttribute.getType());
        }
        QueryOptions queryOptions2 = new QueryOptions();
        queryOptions2.addFilter(Filter.eq("displayName", "Payroll_Admin"));
        Iterator iterator7 = context.search(ManagedAttribute.class, queryOptions2);
        if (!iterator7.hasNext()) {
            log.warn("Payroll Admin doesnt exist");
            return;
        }
        ManagedAttribute managedAttribute = (ManagedAttribute) iterator7.next();
        log.info(managedAttribute.getDisplayName());
        log.info(managedAttribute.getValue());
        log.info(managedAttribute.getApplication().getName());
        if (managedAttribute.getOwner() != null) {
            log.info(managedAttribute.getOwner().getName());
        }
        log.info(managedAttribute.getType());
        QueryOptions queryOptions3 = new QueryOptions();
        queryOptions3.addFilter(Filter.and(
                Filter.eq("application.name", "AD"),
                Filter.like("displayName", "HR_", Filter.MatchMode.ANYWHERE)
        ));
        Iterator iterator8 = context.search(ManagedAttribute.class, queryOptions3);
        while (iterator8.hasNext()) {
            ManagedAttribute managedAttribute1 = (ManagedAttribute) iterator8.next();
            log.info(managedAttribute1.getDisplayName());
            log.info(managedAttribute1.getApplication().getName());
            Identity owner = managedAttribute1.getOwner();
            if (owner != null) {
                log.info(owner.getName());
            } else log.info("Entitlement does not have an owner");
        }
        Identity identity2 = plan.getIdentity();
        if (plan == null || identity2 == null) {
            return;
        }
        if (identity2.getLinks() == null) {
            return;
        }
        for (Link link : identity2.getLinks()) {
            if (link == null) {
                continue;
            }
            if (!"AD".equalsIgnoreCase(link.getApplicationName())) {
                continue;
            }
            Object memberOf = link.getAttribute("memberOf");
            if (memberOf instanceof String) {
                String group = (String) memberOf;
                if ("Payroll_Admin".equalsIgnoreCase(group)) {
                    log.info("Payroll Admin already assigned");
                    return;
                }
            }
            if (memberOf instanceof Collection<?>) {
                Collection groups = (Collection) memberOf;
                for (Object group : groups) {
                    String groupname = String.valueOf(group);
                    if ("Payroll_Admin".equalsIgnoreCase(groupName)) {
                        log.info("Already has Payroll Admin");
                    }
                }
            }
        }
        QueryOptions queryOptions4 = new QueryOptions();
        queryOptions4.addFilter(Filter.eq("department", "HR"));
        Iterator iterator5 = null;
        try {
            iterator5 = context.search(Identity.class, queryOptions4);
            while (iterator5.hasNext()) {
                Identity identity1 = (Identity) iterator5.next();
                log.info(identity1.getName());
                log.info(identity1.getEmail());
            }
        } catch (GeneralException e) {
            log.error("Failed while searching HR Identities", e);
        } finally {
            if (iterator5 != null) {
                Util.flushIterator(iterator5);
            }
        }
        QueryOptions queryOptions5 = new QueryOptions();
        queryOptions5.addFilter(Filter.eq("department", "HR"));
        Iterator iterator9 = null;
        try {
            iterator9 = context.search(Identity.class, queryOptions5, "name,email,department");
            while (iterator9.hasNext()) {
                Object[] row = (Object[]) iterator9.next();
                String name = (String) row[0];
                String email = (String) row[1];
                String department = (String) row[2];
                log.debug((
                        "Name: " + name +
                                ", Department: " + department +
                                ", Email: " + email
                ));
            }
        } catch (GeneralException e) {
            log.error("HR Identity not found");
        } finally {
            Util.flushIterator(iterator9);
        }
        HashMap map = DelimitedFileConnector.defaultBuildMap();
        String firstName = (String) map.get("FIRST_NAME");
        String lastName = (String) map.get("LAST_NAME");
        String country = (String) map.get("COUNTRY");
        String employeeType = (String) map.get("EMPLOYEE_Type");
        String password = (String) map.get("PASSWORD");
        java.sql.Date hireDate = (Date) map.get("HIRE_DATE");
        Timestamp lastLogin = (Timestamp) map.get("LAST_LOGIN");
        if (hireDate!=null){

            map.put("HIRE_DATE",new SimpleDateFormat("dd-MM-yyyy").format(hireDate));
        }
        if (lastLogin!=null){
            map.put("LAST_LOGIN",new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(lastLogin));
        }
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        String displayName = firstName + " " + lastName;
        displayName = displayName.trim();
        map.put("displayName", displayName);
        map.remove("PASSWORD");
        if (country != null) {
            country = country.toUpperCase();
        }
        map.put("COUNTRY", country);
        if ("FT".equalsIgnoreCase(employeeType)) {
            map.put("employeeTpe","FullTime");
        } else if ("PT".equalsIgnoreCase(employeeType)) {
               map.put("employeeType","PartTime");
        }else map.put("employeeType","Contractor");
        //Customization rule started here
        ResourceObject resourceObject = new ResourceObject();
        String department1 = (String) resourceObject.getAttribute("department");
        String country1 = (String) resourceObject.getAttribute("country");
        String displayName1 = (String) resourceObject.getAttribute("displayName");
        String employeeType1 = (String) resourceObject.getAttribute("employeeType");
        if ("001".equalsIgnoreCase(department1)){
            resourceObject.put("department","Finance");
        }
        if ("UK".equalsIgnoreCase(country1)){
resourceObject.put("country","United Kingdom");
        }
        if ("JOHN SMITH".equalsIgnoreCase(displayName1)){
            resourceObject.put("displayName",displayName1.toLowerCase());
        }
        if ("Contractor".equalsIgnoreCase(employeeType1)){
            resourceObject.setAttribute("needsVPN",true);
        }else resourceObject.setAttribute("needsVPN",false);

        //Ad memberOf
        Object memberOf = resourceObject.getAttribute("memberOf");
        if (memberOf instanceof  List){
            List groups = (List) memberOf;
            List cleanedGroups = new ArrayList<>();
            for (Object group : groups){
                String dn = String.valueOf(group);
                int start = dn.indexOf("CN=") + 3;
                int end = dn.indexOf(",");
                String cleanGroup = dn.substring(start,end);
                cleanedGroups.add(cleanGroup);

        }return resourceObject;
        }
        Object memberOfObj = resourceObject.getAttribute("memberOf");
        if (!(memberOfObj instanceof List)){
 return resourceObject;
        }
        List groups1 = (List) memberOfObj;
        List cleanGroup  = new ArrayList<>();
        for (Object group1 : groups1){
            if (group1==null){
                log.warn("Null memberOf found on Identity");
                continue;
            }
            String domainName = String.valueOf(group1).trim();
            if (domainName.isEmpty()){
                log.warn("Skipping null and empty DN values");
                continue;
            }
            int start1 = domainName.indexOf("CN=")+3;
            int end1  =domainName.indexOf(",");
            String cleanGroup1 = domainName.substring(start1,end1);
            cleanGroup.add(cleanGroup1);
        }
String department2 = (String) resourceObject.getAttribute("department");
        if (department2==null || department2.trim().isEmpty()){
            log.info("Department is null or empty");
            return resourceObject;
        }if ("Finance".equalsIgnoreCase(department2)){
            resourceObject.setAttribute("department",department2.toUpperCase());
        }
String employeeType2 = (String) resourceObject.getAttribute("employeeType");
       if ("FT".equalsIgnoreCase(employeeType2)){
           resourceObject.put("employeeType","Full TIme");
       }
       if ("PT".equalsIgnoreCase(employeeType2)){
           resourceObject.setAttribute("employeeType","Part Time");
       }
Object memberOf1 = resourceObject.getAttribute("memberOf");
       if (!(memberOf1 instanceof List)){
       return resourceObject;
       }
      List groups = (List) memberOf1;
       List newCleanGroup = new ArrayList<>();
       for (Object group : groups){
           if (group==null){
               log.info("Skipping null values for group");
               continue;
           }
           String dn = String.valueOf(group).trim();
           if (dn.isEmpty()){
               log.info("DN is empty--Skipping");
               continue;
           }
           try {
           LdapName ldapName = new LdapName(dn);
           List<Rdn> rdns  = ldapName.getRdns();
           if (rdns.isEmpty()){
               continue;
           }
           Rdn lastRdn = rdns.get(rdns.size()-1);
           if (!"CN".equalsIgnoreCase(lastRdn.getType())){
               continue;
           }String groupName1 = String.valueOf(lastRdn.getValue());
           newCleanGroup.add(groupName1);
       }catch (InvalidNameException e){
               log.warn("Unable to parse Ldap DN" + dn,e);
           }
    }
       String mail = (String) account.getAttribute("mail");
       if (Util.isNullOrEmpty(mail)){
return null;
       }
       QueryOptions queryOptions6 = new QueryOptions();
       queryOptions6.addFilter(Filter.eq("email",mail));
       Iterator iterator10 = null;
       try {
 iterator10 = context.search(Identity.class,queryOptions6);
 if (iterator10.hasNext()){
     Identity identity1 = (Identity) iterator10.next();
     Map map1 = new HashMap();
     map1.put("identity",identity1);
     return map1;
 }
       }
       catch (GeneralException e){
           log.error("Correlation rule failed",e);
       }finally {
           Util.flushIterator(iterator10);
       }return null;
       //Correlation Rule--2
        Map resultMap = new HashMap();
        Iterator iterator11 = null;
        Identity identity1 = null;

        if (account==null){
            return null;
        }String employeeId = account.getStringAttribute("employeeId");
        String mail = account.getStringAttribute("mail");
        if (!Util.isNullOrEmpty(employeeId)){
            try {
                QueryOptions queryOptions7 = new QueryOptions();
                queryOptions7.addFilter(Filter.eq("employeeNumber",employeeId));
                iterator11 = context.search(Identity.class,queryOptions7);
                iterator11 = context.search(Identity.class,queryOptions7);
                if (iterator11.hasNext()){
                identity1 = (Identity) iterator11.next();
                if (iterator11.hasNext()) {
                    log.error("Duplicate Identities found for employeeId" + employeeId);
                    return null;

                }resultMap.put("identity",identity1);
                return resultMap;
            }
            }catch (GeneralException e){
                log.error("EmployeeId Correlation rule failed");
            }finally {
                Util.flushIterator(iterator11);
            }
        }log.info("Starting Email Correlation");
        if (!Util.isNullOrEmpty(mail)){
            try {
                QueryOptions queryOptions7  = new QueryOptions();
                queryOptions7.addFilter(Filter.eq("email",mail));
                iterator11 = context.search(Identity.class,queryOptions7);
                if (iterator11.hasNext()){
                    identity1 = (Identity) iterator11.next();
                    resultMap.put("identity",identity1);
                    return resultMap;
                }
            }catch (GeneralException e){
                log.error("Correlation Rule Failed",e);
            }finally {
                Util.flushIterator(iterator11);
            }
        }
        Map resultMap = new HashMap();
        Iterator iterator12 = null;
        String contractorEmail = (String) account.getAttribute("contractorEmail");
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





