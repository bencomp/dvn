<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" 
                        xmlns:h="http://java.sun.com/jsf/html" 
                        xmlns:jsp="http://java.sun.com/JSP/Page" 
                        xmlns:ui="http://www.sun.com/web/ui"
                        xmlns:tiles="http://struts.apache.org/tags-tiles">
    <f:subview id="EditPasswordPageView">
        <ui:form  id="form1">
           <h:inputHidden id="vdcId" value="#{VDCRequest.currentVDCId}"/>
           <h:inputHidden id="hiddenUserId" binding="#{EditPasswordPage.hiddenUserId}" value="#{EditPasswordPage.userId}"/>

           <input type="hidden" name="pageName" value="EditPasswordPage"/>
           <div class="dvn_section">
               <div class="dvn_sectionTitle">
                
                       <h:outputText value="Edit Dataverse Network Account"/>
                 
               </div>            
               <div class="dvn_sectionBox"> 
                   <div class="dvn_margin12"> 
                       <ui:panelGroup block="true" style="padding-bottom: 15px" rendered="#{VDCSession.loginBean.networkAdmin}">
                            <h:graphicImage alt="Information" title="Information" styleClass="vdcNoBorders" style="vertical-align: bottom" value="/resources/icon_info.gif" />
                            <h:outputText styleClass="vdcHelpText" value="If you a Network Administrator updating another users's password, enter the administrator password in the 'Current Password' field."/>
                        </ui:panelGroup>   
                                  
                       <h:panelGrid  cellpadding="0" cellspacing="0"
                                     columnClasses="vdcColPadded, vdcColPadded" columns="2" id="gridPanel2">
                      
                           <ui:panelGroup >
                               <h:outputText   value="Current Password"/>
                               <h:graphicImage  id="image1" value="/resources/icon_required.gif"/>
                           </ui:panelGroup>
                           <ui:panelGroup>
                               <h:inputSecret id="inputCurrentPassword"  validator="#{EditPasswordPage.validateOldPassword}" value="#{EditPasswordPage.editUserService.currentPassword}" required="true">
                               </h:inputSecret>
                               <h:message styleClass="errorMessage" for="inputCurrentPassword"/>
                           </ui:panelGroup>
                           <ui:panelGroup >
                               <h:outputText  value="New Password"/>
                               <h:graphicImage  value="/resources/icon_required.gif"/>
                           </ui:panelGroup>
                           <ui:panelGroup>
                               <h:inputSecret id="inputNewPassword1" binding="#{EditPasswordPage.inputNewPassword}"  value="#{EditPasswordPage.editUserService.newPassword1}" required="true"/> 
                               <h:message styleClass="errorMessage" for="inputNewPassword1"/>
                           </ui:panelGroup>
                           
                           
                           <ui:panelGroup >
                               <h:outputText  value="Retype New Password"/>
                               <h:graphicImage  value="/resources/icon_required.gif"/>
                           </ui:panelGroup>
                           <ui:panelGroup>
                               <h:inputSecret id="inputNewPassword2" validator="#{EditPasswordPage.validateConfirmPassword}" value="#{EditPasswordPage.editUserService.newPassword2}" required="true"/> 
                               <h:message styleClass="errorMessage" for="inputNewPassword2"/>
                           </ui:panelGroup>
                           
                             </h:panelGrid>
                      
                        
                       <ui:panelGroup block="true"  style="padding-left: 100px; padding-top: 20px">
                           <h:commandButton  value="Save" action="#{EditPasswordPage.save}"/>
                            
                       </ui:panelGroup>
                     
                   </div>
               </div>
           </div>
                        
        </ui:form>
    </f:subview>
</jsp:root>
