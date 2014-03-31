/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dvn.core.web.login;

import edu.harvard.iq.dvn.core.admin.EditUserService;
import edu.harvard.iq.dvn.core.admin.UserServiceLocal;
import edu.harvard.iq.dvn.core.admin.VDCUser;
import edu.harvard.iq.dvn.core.mail.MailServiceLocal;
import edu.harvard.iq.dvn.core.study.Study;
import edu.harvard.iq.dvn.core.study.StudyServiceLocal;
import edu.harvard.iq.dvn.core.util.StringUtil;
import edu.harvard.iq.dvn.core.web.common.StatusMessage;
import edu.harvard.iq.dvn.core.web.common.VDCBaseBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mpieters
 */
public class SamlAddAccountPage extends VDCBaseBean implements java.io.Serializable {

    private final static Logger LOGGER = Logger.getLogger(SamlAddAccountPage.class.getPackage().getName());
    @EJB
    EditUserService editUserService;
    @EJB
    UserServiceLocal userService;
    @EJB
    MailServiceLocal mailService;
    @EJB
    StudyServiceLocal studyService;
    
    public String username;
    public String givenname;
    public String surname;
    public String prefix;
    public String email;
    public String usertype;
    public String organization;
    public String infostring;
    private VDCUser user;
    private String workflow;
    private Long studyId;
    private Study study;
    private Boolean allowadmin = false;
    public Boolean createFailed = false;
    public String errMessage = "";

    public SamlAddAccountPage() {
    }

    @Override
    public void init() {
        super.init();
        if (isFromPage("SamlAddAccountPage")) {
            editUserService = (EditUserService) sessionGet(editUserService.getClass().getName());
            user = editUserService.getUser();
        } else {
            editUserService.newUser();
            sessionPut(editUserService.getClass().getName(), editUserService);
            user = editUserService.getUser();

            String usrusername = this.getUsername();
            user.setUserName(usrusername);
            String usrgivenname = this.getGivenname();
            user.setFirstName(usrgivenname);
            String usrprefix = this.getPrefix();
            String usrsurname = this.getSurname();
            String totalsurname = usrprefix + " " + usrsurname;
            user.setLastName(totalsurname.trim());
            String usremail = this.getEmail();
            user.setEmail(usremail);
            String usrorg = this.getOrganization();
            user.setInstitution(usrorg);

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (request.getAttribute("studyId") != null) {
                studyId = new Long(request.getAttribute("studyId").toString());
                editUserService.setRequestStudyId(studyId);
            } else if (this.getRequestParam("studyId") != null) {
                studyId = new Long(Long.parseLong(getRequestParam("studyId")));
                editUserService.setRequestStudyId(studyId);
            }
        }
        studyId = editUserService.getRequestStudyId();
        if (studyId != null) {
            study = studyService.getStudy(studyId);
        }

    }

    public String getUsername() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String usrgivenname;

        if (session.getAttribute("usrusername") != null) {
            usrgivenname = session.getAttribute("usrusername").toString();
            setGivenname(usrgivenname);
        } else {
            usrgivenname = "";
            setGivenname(usrgivenname);
        }

        return usrgivenname;
    }

    public void setUsername(String usrusername) {
        this.username = usrusername;
    }

    public String getGivenname() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String usrgivenname;

        if (session.getAttribute("usrgivenname") != null) {
            usrgivenname = session.getAttribute("usrgivenname").toString();
            setGivenname(usrgivenname);
        } else {
            usrgivenname = "";
            setGivenname(usrgivenname);
        }

        return usrgivenname;
    }

    public void setGivenname(String usrgivenname) {
        this.givenname = usrgivenname;
    }

    public String getSurname() {
        String usrsurname;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (session.getAttribute("usrsurname") != null) {
            usrsurname = session.getAttribute("usrsurname").toString();
            setSurname(usrsurname);
        } else {
            usrsurname = "";
            setSurname(usrsurname);
        }

        return usrsurname;
    }

    public void setSurname(String usrsurname) {
        this.surname = usrsurname;
    }

    public String getPrefix() {
        String usrprefix;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (session.getAttribute("usrprefix") != null) {
            usrprefix = session.getAttribute("usrprefix").toString();
            setPrefix(usrprefix);
        } else {
            usrprefix = "";
            setPrefix(usrprefix);
        }

        return usrprefix;
    }

    public void setPrefix(String usrprefix) {
        this.prefix = usrprefix;
    }

    public String getEmail() {
        String usremail;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        Object emailAttr = session.getAttribute("usremail"); 
        if (emailAttr != null) {
            if (emailAttr instanceof String) {
                usremail = emailAttr.toString();
            } else if (emailAttr instanceof List && ((List) emailAttr).size() > 0) {
                usremail = (String) ((List) emailAttr).get(0);
            } else {
                usremail = null;
            }
            setEmail(usremail);
        } else {
            usremail = "";
            setEmail(usremail);
        }

        return usremail;
    }

    public void setEmail(String usremail) {
        this.email = usremail;
    }

    public String getUsertype() {
        String usrusertype;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (session.getAttribute("usrusertype") != null) {
            usrusertype = session.getAttribute("usrusertype").toString();
            setUsertype(usrusertype);
        } else {
            usrusertype = "user";
            setUsertype(usrusertype);
        }

        return usrusertype;
    }

    public void setUsertype(String usrtype) {
        this.usertype = usrtype;
    }

    public String getInfostring() {
        String usrinfo;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (session.getAttribute("infostring") != null) {
            usrinfo = session.getAttribute("infostring").toString();
            setInfostring(usrinfo);
        } else {
            usrinfo = "lege string";
            setInfostring(usrinfo);
        }

        return usrinfo;
    }

    public void setInfostring(String usrinfo) {
        this.infostring = usrinfo;
    }

    public String getOrganization() {
        String org;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (session.getAttribute("usrorg") != null) {
            org = session.getAttribute("usrorg").toString();
            setOrganization(org);
        } else {
            org = "";
            setOrganization(org);
        }

        return org;
    }

    public void setOrganization(String org) {
        this.organization = org;
    }

    /**
     * Getter for property contributorRequest.
     * @return Value of property contributorRequest.
     */
    public String getWorkflow() {
        return this.workflow;
    }

    /**
     * Setter for property contributorRequest.
     * @param contributorRequest New value of property contributorRequest.
     */
    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public void setAllowAdmin(Boolean allowadmin) {
        this.allowadmin = allowadmin;
    }

    public Boolean getAllowAdmin() {
        Boolean _allowadmin;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (session.getAttribute("ALLOW_ADMIN") != null) {
            _allowadmin = (Boolean) session.getAttribute("ALLOW_ADMIN");
            setAllowAdmin(_allowadmin);
        } else {
            _allowadmin = false;
            setAllowAdmin(_allowadmin);
        }
        return _allowadmin;
    }

    public String createAccount() {
        String forwardPage = null;
        Long contributorRequestVdcId = null;
        String workflowValue = null;

        //trying to set proper permissions
        String workflowusertype = getUsertype();

        Boolean doCreate = "user".equalsIgnoreCase(workflowusertype)
                || "creator".equalsIgnoreCase(workflowusertype)
                || ("admin".equalsIgnoreCase(workflowusertype) && getAllowAdmin());

        if (doCreate) {
            user.setActive(true);
            editUserService.save();
            LOGGER.log(Level.INFO, "Trying to create user");
            if (StringUtil.isEmpty(workflowValue)) {
                StatusMessage msg = new StatusMessage();
                msg.setMessageText("User account created successfully.");
                msg.setStyleClass("successMessage");
                getRequestMap().put("statusMessage", msg);
                forwardPage = "viewAccount";
            }
            LoginWorkflowBean loginWorkflowBean = (LoginWorkflowBean) SamlAddAccountPage.getBean("LoginWorkflowBean");
            loginWorkflowBean.processAddAccount(user);
            LOGGER.log(Level.INFO, "User created (ID={0}); setting permissions", user.getId());

            if ("admin".equalsIgnoreCase(workflowusertype)) {
                LOGGER.log(Level.INFO, "User is network admin");
                userService.makeNetworkAdmin(user.getId());
            } else if ("creator".equalsIgnoreCase(workflowusertype)) {
                LOGGER.log(Level.INFO, "User is dataverse creator");
                userService.makeCreator(user.getId());
            } else {
                LOGGER.log(Level.INFO, "User is a standard user");
            }
            // Login after account creation
            return loginWorkflowBean.processLogin(user, null);
        } else {
            editUserService.cancel();
            LOGGER.log(Level.SEVERE, "Unable to create the account");
            setCreateFailed(true);
            setErrMessage("Your account cannot be created. Please, consult your administrator. Click <strong>Log out</strong> to log out from your federation account and return to the home page.");
            return null;
        }
    }

    public String cancel() {
        editUserService.cancel();

        // if user is logged in return to the appropriate options page
        // if not logged in, to the appropriate home page
        if (getVDCSessionBean().getLoginBean() != null) {
            if (getVDCRequestBean().getCurrentVDC() != null) {
                return "cancelVDC";
            } else {
                return "cancelNetwork";
            }
        } else {
            return getVDCRequestBean().home();
        }
    }

    public void setCreateFailed(Boolean createFailed) {
        this.createFailed = createFailed;
    }

    public Boolean getCreateFailed() {
        return this.createFailed;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return this.errMessage;
    }
}
