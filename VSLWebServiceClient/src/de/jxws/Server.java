
package de.jxws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "Server", targetNamespace = "http://jxws.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Server {


    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "log", targetNamespace = "http://jxws.de/", className = "de.jxws.Log")
    @ResponseWrapper(localName = "logResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.LogResponse")
    public void log(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "connect", targetNamespace = "http://jxws.de/", className = "de.jxws.Connect")
    @ResponseWrapper(localName = "connectResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ConnectResponse")
    public boolean connect();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDir", targetNamespace = "http://jxws.de/", className = "de.jxws.GetDir")
    @ResponseWrapper(localName = "getDirResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetDirResponse")
    public String getDir();

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://jxws.de/", className = "de.jxws.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.LoginResponse")
    public int login(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "registration", targetNamespace = "http://jxws.de/", className = "de.jxws.Registration")
    @ResponseWrapper(localName = "registrationResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.RegistrationResponse")
    public boolean registration(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteuser", targetNamespace = "http://jxws.de/", className = "de.jxws.Deleteuser")
    @ResponseWrapper(localName = "deleteuserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.DeleteuserResponse")
    public boolean deleteuser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "activateuser", targetNamespace = "http://jxws.de/", className = "de.jxws.Activateuser")
    @ResponseWrapper(localName = "activateuserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ActivateuserResponse")
    public boolean activateuser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addTermin", targetNamespace = "http://jxws.de/", className = "de.jxws.AddTermin")
    @ResponseWrapper(localName = "addTerminResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.AddTerminResponse")
    public boolean addTermin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        String arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        String arg7);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "removeTermin", targetNamespace = "http://jxws.de/", className = "de.jxws.RemoveTermin")
    @ResponseWrapper(localName = "removeTerminResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.RemoveTerminResponse")
    public boolean removeTermin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @param arg8
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "changeTermin", targetNamespace = "http://jxws.de/", className = "de.jxws.ChangeTermin")
    @ResponseWrapper(localName = "changeTerminResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ChangeTerminResponse")
    public boolean changeTermin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        String arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        String arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        String arg8);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showKalender", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowKalender")
    @ResponseWrapper(localName = "showKalenderResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowKalenderResponse")
    public List<String> showKalender(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getKonstante", targetNamespace = "http://jxws.de/", className = "de.jxws.GetKonstante")
    @ResponseWrapper(localName = "getKonstanteResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetKonstanteResponse")
    public int getKonstante(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getRights", targetNamespace = "http://jxws.de/", className = "de.jxws.GetRights")
    @ResponseWrapper(localName = "getRightsResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetRightsResponse")
    public int getRights(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showAllUsers", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowAllUsers")
    @ResponseWrapper(localName = "showAllUsersResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowAllUsersResponse")
    public List<String> showAllUsers(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "refreshList", targetNamespace = "http://jxws.de/", className = "de.jxws.RefreshList")
    @ResponseWrapper(localName = "refreshListResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.RefreshListResponse")
    public List<String> refreshList();

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "setDataUsed", targetNamespace = "http://jxws.de/", className = "de.jxws.SetDataUsed")
    @ResponseWrapper(localName = "setDataUsedResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.SetDataUsedResponse")
    public void setDataUsed(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isDataUsed", targetNamespace = "http://jxws.de/", className = "de.jxws.IsDataUsed")
    @ResponseWrapper(localName = "isDataUsedResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.IsDataUsedResponse")
    public boolean isDataUsed(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "releaseData", targetNamespace = "http://jxws.de/", className = "de.jxws.ReleaseData")
    @ResponseWrapper(localName = "releaseDataResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ReleaseDataResponse")
    public void releaseData(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "giveIP", targetNamespace = "http://jxws.de/", className = "de.jxws.GiveIP")
    @ResponseWrapper(localName = "giveIPResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GiveIPResponse")
    public void giveIP(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "changepasswort", targetNamespace = "http://jxws.de/", className = "de.jxws.Changepasswort")
    @ResponseWrapper(localName = "changepasswortResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ChangepasswortResponse")
    public boolean changepasswort(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deactivateuser", targetNamespace = "http://jxws.de/", className = "de.jxws.Deactivateuser")
    @ResponseWrapper(localName = "deactivateuserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.DeactivateuserResponse")
    public boolean deactivateuser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showNotActivatedUsers", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowNotActivatedUsers")
    @ResponseWrapper(localName = "showNotActivatedUsersResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowNotActivatedUsersResponse")
    public List<String> showNotActivatedUsers(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showUsersActiveInLastTenMin", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowUsersActiveInLastTenMin")
    @ResponseWrapper(localName = "showUsersActiveInLastTenMinResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowUsersActiveInLastTenMinResponse")
    public List<String> showUsersActiveInLastTenMin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showIPsToChat", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowIPsToChat")
    @ResponseWrapper(localName = "showIPsToChatResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowIPsToChatResponse")
    public List<String> showIPsToChat(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIPFromUser", targetNamespace = "http://jxws.de/", className = "de.jxws.GetIPFromUser")
    @ResponseWrapper(localName = "getIPFromUserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetIPFromUserResponse")
    public String getIPFromUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "createCalenderfromInstanz", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateCalenderfromInstanz")
    @ResponseWrapper(localName = "createCalenderfromInstanzResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateCalenderfromInstanzResponse")
    public void createCalenderfromInstanz(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "showKalenderFrom", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowKalenderFrom")
    @ResponseWrapper(localName = "showKalenderFromResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.ShowKalenderFromResponse")
    public List<String> showKalenderFrom(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "calenderToString", targetNamespace = "http://jxws.de/", className = "de.jxws.CalenderToString")
    @ResponseWrapper(localName = "calenderToStringResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.CalenderToStringResponse")
    public String calenderToString(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getServerFileasString", targetNamespace = "http://jxws.de/", className = "de.jxws.GetServerFileasString")
    @ResponseWrapper(localName = "getServerFileasStringResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetServerFileasStringResponse")
    public String getServerFileasString(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "createFilefromClient", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateFilefromClient")
    @ResponseWrapper(localName = "createFilefromClientResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateFilefromClientResponse")
    public void createFilefromClient(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "giveAllUsernames", targetNamespace = "http://jxws.de/", className = "de.jxws.GiveAllUsernames")
    @ResponseWrapper(localName = "giveAllUsernamesResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GiveAllUsernamesResponse")
    public String giveAllUsernames();

    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "createUserListfromData", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateUserListfromData")
    @ResponseWrapper(localName = "createUserListfromDataResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateUserListfromDataResponse")
    public void createUserListfromData();

    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "createTodoListfromData", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateTodoListfromData")
    @ResponseWrapper(localName = "createTodoListfromDataResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.CreateTodoListfromDataResponse")
    public void createTodoListfromData();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getinternIPfromUser", targetNamespace = "http://jxws.de/", className = "de.jxws.GetinternIPfromUser")
    @ResponseWrapper(localName = "getinternIPfromUserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetinternIPfromUserResponse")
    public String getinternIPfromUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getexternIPfromUser", targetNamespace = "http://jxws.de/", className = "de.jxws.GetexternIPfromUser")
    @ResponseWrapper(localName = "getexternIPfromUserResponse", targetNamespace = "http://jxws.de/", className = "de.jxws.GetexternIPfromUserResponse")
    public String getexternIPfromUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
