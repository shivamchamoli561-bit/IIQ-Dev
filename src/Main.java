import openconnector.Plan;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sailpoint.connector.JDBCConnector;
import sailpoint.object.*;
import sailpoint.tools.GeneralException;
import sailpoint.tools.Util;
import sailpoint.web.accessrequest.AccessRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args){
        ProvisioningPlan plan = new ProvisioningPlan();
        List accountRequests = plan.getAccountRequests();
        ProvisioningPlan.AttributeRequest vpnReq = null;
        ProvisioningPlan.AccountRequest awsReq = null;
        Identity identity = context.getObjectByName(Identity.class,"Alice");
        if (identity == null)
            return;
        Link azurelink = null;
        for (Link link : identity.getLinks()){
            System.out.println(link.getApplicationName());
            if ("AWS".equalsIgnoreCase(link.getApplicationName())){
                System.out.println("Privileged Account Found");
            break;}
        } for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName()))
                for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                    if ("Admin".equalsIgnoreCase(atr.getValue().toString())){
                        System.out.println("Add Audit Group");
                        break;
                    }
                }
                }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName()))
                for(ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                    if ("Admin".equalsIgnoreCase(atr.getValue().toString())){
                        ProvisioningPlan.AttributeRequest reportingGroup = new ProvisioningPlan.AttributeRequest();
                        ar.add(reportingGroup);
                    }
                    if ("Read".equalsIgnoreCase(atr.getValue().toString())){
                        ProvisioningPlan.AttributeRequest reportingGroup = new ProvisioningPlan.AttributeRequest();
                        ar.add(reportingGroup);
                    }
                }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName()))
                for(ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                   if ("Admin".equalsIgnoreCase(atr.getValue().toString())){
                       ProvisioningPlan.AttributeRequest auditGroup = new ProvisioningPlan.AttributeRequest();
                       auditGroup.setName("Group");
                       auditGroup.setOperation(ProvisioningPlan.Operation.Add);
                       auditGroup.setValue("Audit Group");
                       ar.add(auditGroup);
                   }
                }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("Azure".equalsIgnoreCase(ar.getApplicationName()))
                for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                    if ("Reader".equalsIgnoreCase(atr.getValue().toString())){
                        ProvisioningPlan.AttributeRequest readerGroup = new ProvisioningPlan.AttributeRequest();
                        readerGroup.setName("Group");
                        readerGroup.setOperation(ProvisioningPlan.Operation.Add);
                        readerGroup.setValue("Reader Group");
                        ar.add(readerGroup);
                    }
                }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            for(ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("Department".equalsIgnoreCase(atr.getName())
                && "HR".equalsIgnoreCase(atr.getValue().toString())){
                    ProvisioningPlan.AttributeRequest hrPortal = new ProvisioningPlan.AttributeRequest();
                    hrPortal.setName("Group");
                    hrPortal.setOperation(ProvisioningPlan.Operation.Add);
                    hrPortal.setValue("HR-Portal");
                    ar.add(hrPortal);
                }
            }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName()))
                for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                    if ("Admin".equalsIgnoreCase(atr.getValue().toString())){
                        ProvisioningPlan.AttributeRequest auditGroup = new ProvisioningPlan.AttributeRequest();
                        auditGroup.setName("Group");
                        auditGroup.setOperation(ProvisioningPlan.Operation.Add);
                        auditGroup.setValue("Audit Group");
                        ar.add(auditGroup);
                        break;
                    }if ("Read".equalsIgnoreCase(atr.getValue().toString())){
                        ProvisioningPlan.AttributeRequest reportingGroup = new ProvisioningPlan.AttributeRequest();
                        reportingGroup.setName("Group");
                        reportingGroup.setOperation(ProvisioningPlan.Operation.Add);
                        reportingGroup.setValue("Reporting Group");
                        ar.add(reportingGroup);
                    }
                }
        }for (ProvisioningPlan.AccountRequest ac : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ac.getApplicationName())){
                awsReq = ac;
                for (ProvisioningPlan.AttributeRequest atr : awsReq.getAttributeRequests()){
                    if ("VPN".equalsIgnoreCase(atr.getValue().toString())){
                        vpnReq = atr;
                        break;
                    }
                }break;
            }
        } if (awsReq == null || vpnReq ==null)
            return;
        awsReq.remove(vpnReq);
        String dept = null;
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("Department".equalsIgnoreCase(atr.getName())){
                   dept = atr.getValue().toString();
            }
        }
}String location = null;
        String country = null;
        for (ProvisioningPlan.AccountRequest ar  : plan.getAccountRequests()){
            for(ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("Location".equalsIgnoreCase(atr.getName())){
                    location = atr.getValue().toString();
                }if ("Country".equalsIgnoreCase(atr.getName())){
                    country = atr.getValue().toString();
                }
            }
        } String employeeType = null;
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("EmployeeType".equalsIgnoreCase(atr.getName())
                && "Contractor".equalsIgnoreCase(atr.getValue().toString())){
                    employeeType = atr.getValue().toString();
System.out.println("Contractor Detected");
                }
            }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("HR".equalsIgnoreCase(dept)
                && "UK".equalsIgnoreCase(location)){
                    System.out.println("Add UK-Group");
                }
            }
        }String department = null;
        String loc = null;
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("Department".equalsIgnoreCase(atr.getName())){
                    department = atr.getValue().toString();
                }else if ("Location".equalsIgnoreCase(atr.getName())){
                    loc = atr.getValue().toString();
                }
            }
        } if ("HR".equalsIgnoreCase(department)
        && "UK".equalsIgnoreCase(loc)){
            System.out.println("Add UK-HR Group");
        }
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName())){
                if (ProvisioningPlan.AccountRequest.Operation.Create.equals(ar.getOperation())){
                    ProvisioningPlan.AttributeRequest auditGroup = new ProvisioningPlan.AttributeRequest();
                    auditGroup.setName("Group");
                    auditGroup.setOperation(ProvisioningPlan.Operation.Add);
                    auditGroup.setValue("Audit Group");
                    ar.add(auditGroup);
                }
            }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if("AWS".equalsIgnoreCase(ar.getApplicationName())){
                if (ProvisioningPlan.AccountRequest.Operation.Create.equals(ar.getOperation())){
                    ProvisioningPlan.AttributeRequest auditGroup = new ProvisioningPlan.AttributeRequest();
                    auditGroup.setName("Group");
                    auditGroup.setOperation(ProvisioningPlan.Operation.Add);
                    auditGroup.setValue("Audit Group");
                    ar.add(auditGroup);
                }else if (ProvisioningPlan.AccountRequest.Operation.Disable.equals(ar.getOperation())){
                    ProvisioningPlan.AttributeRequest disableAuditGrooup = new ProvisioningPlan.AttributeRequest();
                disableAuditGrooup.setName("Group");
                disableAuditGrooup.setOperation(ProvisioningPlan.Operation.Add);
                disableAuditGrooup.setValue("Disable Audit Group");
                ar.add(disableAuditGrooup);
                }
            }
        }for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if ("AWS".equalsIgnoreCase(ar.getApplicationName())){
                if (ProvisioningPlan.AccountRequest.Operation.Create.equals(ar.getOperation()))
                    for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                        if ("Role".equalsIgnoreCase(atr.getName())
                                && "Admin".equalsIgnoreCase(atr.getValue().toString())){
                            ProvisioningPlan.AttributeRequest auditGroup = new ProvisioningPlan.AttributeRequest();
                            auditGroup.setName("Group");
                            auditGroup.setOperation(ProvisioningPlan.Operation.Add);
                            auditGroup.setValue("Audit Group");
                            ar.add(auditGroup);
                            break;
                        }
                    }
            }
        }String dep = null;
        String loca = null;
        boolean createFound = false;
        ProvisioningPlan.AccountRequest targetAr = null;
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if (ProvisioningPlan.AccountRequest.Operation.Create.equals(ar.getOperation())){
                createFound = true;
                targetAr = ar;
                for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                  if ("Department".equalsIgnoreCase(atr.getName()))
                      dep = atr.getValue().toString();
                  else if ("Location".equalsIgnoreCase(atr.getName()))
                      loca  = atr.getValue().toString();
                }
        }
        }
        if ("HR".equalsIgnoreCase(dep)
        && "UK".equalsIgnoreCase(loca)
        && createFound
        && targetAr!=null) {
            ProvisioningPlan.AttributeRequest ukHr = new ProvisioningPlan.AttributeRequest();
            ukHr.setName("Group");
            ukHr.setOperation(ProvisioningPlan.Operation.Add);
            ukHr.setValue("UK-HR Group");
            targetAr.add(ukHr);
        }
        for (ProvisioningPlan.AccountRequest ar : plan.getAccountRequests()){
            if (!ProvisioningPlan.AccountRequest.Operation.Create.equals(ar.getOperation())
            && !"AWS".equalsIgnoreCase(ar.getApplicationName())){
                continue;
            }String depart = null;
            String locat = null;
            for (ProvisioningPlan.AttributeRequest atr : ar.getAttributeRequests()){
                if ("Department".equalsIgnoreCase(atr.getName()))
                    depart = atr.getValue().toString();
                else if ("Location".equalsIgnoreCase(atr.getName()))
                    locat = atr.getValue().toString();
            }if ("HR".equalsIgnoreCase(depart)
            && "UK".equalsIgnoreCase(locat)){
                ProvisioningPlan.AttributeRequest ukHrGroup = new ProvisioningPlan.AttributeRequest();
                ukHrGroup.setName("Group");
                ukHrGroup.setOperation(ProvisioningPlan.Operation.Add);
                ukHrGroup.setValue("UK_HR Group");
            }
        }
        HashMap map = JDBCConnector.buildMapFromResultSet(result);
        Date hireDate = (Date) map.get("HIRE_DATE");
        Timestamp lastLogin   = (Timestamp) map.get("TIME_STAMP");
        Date terminationDate = (Date) map.get("TERMINATION_DATE");
        Timestamp lastPassChange = (Timestamp) map.get("LAST_PASSWORD_CHANGE");
        String firstName = (String) map.get("FIRST_NAME");
        String lastName = (String) map.get("LAST_NAME");
        Date lastWorkingDay = (Date) map.get("LAST_WORKING_DAY");
        if (hireDate!=null){
            map.put("HIRE_DATE",new SimpleDateFormat("dd-MM-yyyy").format(hireDate));
        }
if (lastLogin!=null){
    map.put("TIME_STAMP",new SimpleDateFormat("dd-MMM-yyyy hh:mm a").format(lastLogin));
}
if (terminationDate==null){
    map.put("EMPLOYEE_STATUS","Active");
}else {
    map.put("TERMINATION_DATE", new SimpleDateFormat("dd-MMM-yyyy").format(terminationDate));
    map.put("EMPLOYEE_STATUS","Terminated");
}
if (lastPassChange!=null){
    String formattedDate = new SimpleDateFormat("dd-MMM-yy").format(lastPassChange);
    map.put("PasswordChangedDate",formattedDate);
}
if (firstName==null){
    firstName = "";
}
if (lastName==null){
    lastName = "";
} map.put("displayName",(firstName + "" + lastName).trim());
if (hireDate!=null){
    map.put("HIRE_DATE",new SimpleDateFormat("dd-MMM-yyyy").format(hireDate));
}
if (lastLogin!=null){
    map.put("LAST_LOGIN",new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(lastLogin));
}
if (lastLogin==null){
    map.put("NeverLoggedIn",true);
} else  map.put("NeverLoggedIn",false);
if (lastWorkingDay!=null) {
    String displayDate = new SimpleDateFormat(
            "EEEE, dd MMM yyyy"
    ).format(lastWorkingDay);
    map.put("LastWorkingDayDisplay", displayDate);
}
Log log  = LogFactory.getLog("ProjectHub_Correlation_Rule");
log.info("ProjectHub_Correlation_Rule");
Map resultMap  = new HashMap();
Iterator iterator = null;
if (account==null){
    return null;
}String employeeCode = account.getStringAttribute("hrEmployeeCode");
String mail = account.getStringAttribute("corporateEmail");
log.debug("Starting Correlation using EmployeeId");
if (!Util.isNullOrEmpty(employeeCode)){
    try {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.add(Filter.eq("employeeId",employeeCode));
        iterator = context.search(Identity.class,queryOptions);
        if (iterator.hasNext()){
            Identity identity =  (Identity) iterator.next();
            resultMap.put("identity",identity);
            return resultMap;
        }
    } catch (GeneralException e) {
        log.error("EmployeeId Correlation Rule Failed", e);
    }finally {
        Util.flushIterator(iterator);
    }
}       log.info("Starting Correlation by email only if employeeIf fails");
if (Util.isNotNullOrEmpty(mail)){
    try {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.add(Filter.eq("email",mail));
        iterator = context.search(Identity.class,queryOptions);
        if (iterator.hasNext()){
            Identity identity   = (Identity) iterator.next();
            resultMap.put("identity",identity);
            return resultMap;
        }
    }  catch (GeneralException e){
        log.error("Email Correlation Rule Failed");
    }                                          finally {
        Util.flushIterator(iterator);
    }
} return null;
    }
}