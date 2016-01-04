package shihab;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import edu.illinois.cs.cogcomp.lbjava.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
 import java.io.File;

public class myDocument{
    /* Used Variables*/ 
    public String lebel, lebel_PL,lebel_PT,lebel_OL,lebel_OT;
    public int GuessPersonID;
    public int GuessOrganizationID;
    public int GuessLocationID;
    public int GuessTimeID;    
    public int PersonCount_in_Document=0;
    public int OrgCount_in_Document=0;
    public int LocationCount_in_Document = 0;
    public int TimeCount_in_Document=0;
    public double totalSentence = 0;
    public double np=0,nl=0,not_np=0,not_nl=0, coExis_PL = 0,coExis_PT =0 ;        
    public double no=0,nt=0,not_no=0,not_nt=0;
    public double coExis_OL = 0,coExis_OT= 0, coExis_PLT=0, coExis_OLT=0,coExis_LT=0;
    public double entConcentration_P=0,entConcentration_T=0,entConcentration_L=0,entConcentration_O=0;
    public double quotation_PL=0,quotation_PT =0,quotation_OL =0,quotation_OT =0;
    Element docEle;
    int title_match_P = 0,title_match_O =0,title_match_T =0,title_match_L =0;






    /* Used Variables*/ 
    public void Preparation(File file) throws IOException, ParserConfigurationException, SAXException{                
        
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse (file);
        doc.getDocumentElement().normalize ();
         docEle = doc.getDocumentElement();   
    }
    public void ProcessLabel(){
       
            NodeList nl_answer = docEle.getElementsByTagName("answer");
            Node AnswerNode = nl_answer.item(0);
            Element AnswerNodeElement = (Element)AnswerNode;
            this.lebel = AnswerNodeElement.getAttribute("accuracy");
            this.lebel_PL = AnswerNodeElement.getAttribute("lebel_PL");
            this.lebel_PT = AnswerNodeElement.getAttribute("lebel_PT");
            this.lebel_OL = AnswerNodeElement.getAttribute("lebel_OL");
            this.lebel_OT = AnswerNodeElement.getAttribute("lebel_OT");
                  
    }
    
    public double getQuotationValue_PL(){      return this.quotation_PL; }
    public double getQuotationValue_PT(){      return this.quotation_PT; }
    public double getQuotationValue_OL(){      return this.quotation_OL; }
    public double getQuotationValue_OT(){      return this.quotation_OT; }

    public int getCount_person_name(){      return this.PersonCount_in_Document; }
    public int getCount_location_name(){      return this.LocationCount_in_Document; }
    public int getCount_time_name(){      return this.TimeCount_in_Document; }
    public int getCount_ORG_name(){      return this.OrgCount_in_Document; }
    
    public double getCoExistence_PersonLocation(){      return this.coExis_PL; }
    public double getCoExistence_OrgLocation(){      return this.coExis_OL; }
    public double getCoExistence_PersonTime(){      return this.coExis_PT; }
    public double getCoExistence_OrgTime(){      return this.coExis_OT; }
    public double getCoExistence_LT(){      return this.coExis_LT; }
    public double getCoExistence_PLT(){      return this.coExis_PLT; }
    public double getCoExistence_OLT(){      return this.coExis_OLT; }
     public double getMatchExist_titled_location(){      return this.title_match_L; }
     public double getMatchExist_titled_person(){  return this.title_match_P; }
     public double getMatchExist_titled_org(){ return this.title_match_O; }
     public double getMatchExist_titled_time(){ return this.title_match_T; }
    
    
    public double getEntConcentration_time_name(){      return (this.entConcentration_T*(-1.0)) /this.totalSentence    ; }
    public double getEntConcentration_org_name(){      return (this.entConcentration_O*(-1.0)) /this.totalSentence ; }
    public double getEntConcentration_person_name(){      return (this.entConcentration_P*(-1.0)) /this.totalSentence ; }
    public double getEntConcentration_location_name(){      return (this.entConcentration_L*(-1.0)) /this.totalSentence ; }
    public double getSum_Entconcentrations_PL(){      return this.entConcentration_P+this.entConcentration_L ;  }    
    public double getSum_Entconcentrations_OL(){      return this.entConcentration_O+this.entConcentration_L ; }
    public double getSum_Entconcentrations_OT(){      return this.entConcentration_O+this.entConcentration_T ; }
    public double getSum_Entconcentrations_PT(){      return this.entConcentration_P+this.entConcentration_T ; }
	
    public void GuessProcessing(){
                    
            NodeList nl_Guess = docEle.getElementsByTagName("Guess");
            Node GuessNode = nl_Guess.item(0);
            Element GuessNodeElement = (Element)GuessNode;
            GuessPersonID = Integer.parseInt(GuessNodeElement.getAttribute("PersonID"));
            GuessOrganizationID = Integer.parseInt(GuessNodeElement.getAttribute("OrganizationID"));
            GuessLocationID = Integer.parseInt(GuessNodeElement.getAttribute("LocationID"));
            GuessTimeID = Integer.parseInt(GuessNodeElement.getAttribute("TimeID"));
    }
    
    void textProcessing(){
        
            /* text Processing*/
            int t_entConcentration_P=0,t_entConcentration_T=0,t_entConcentration_L=0,t_entConcentration_O=0;// temp for entConc
             NodeList nl_text = docEle.getElementsByTagName("text");
             Node textNode = nl_text.item(0);
             Element textNode_E = (Element)textNode;
             NodeList nl_s = textNode_E.getElementsByTagName("s");
             totalSentence = nl_s.getLength();
 ////////////////////////////////// think down to single sentence ////////////////////////////////////
    for(int ss=0; ss<nl_s.getLength() ; ss++){ 
        Node nl_s_0 = nl_s.item(ss);
        Element nl_s_0_E = (Element)nl_s_0;
        
        //////////////////////////////////////////////////
        NodeList nl_PERSON  =  nl_s_0_E.getElementsByTagName("PERSON");
        NodeList nl_ORG  =  nl_s_0_E.getElementsByTagName("Organization");
        NodeList nl_LOCATION  =  nl_s_0_E.getElementsByTagName("LOCATION");
        NodeList nl_TIME  =  nl_s_0_E.getElementsByTagName("TIME");        
        Node  N_P,N_l, N_T, N_O;
        Element N_P_E,N_l_E, N_T_E, N_O_E;  
        int flag_coExis_P = 0,flag_coExis_L = 0, flag_coExis_T =0, flag_coExis_O =0  ;  
        
        np=0;nl=0;not_np=0;not_nl=0;    
        no=0;nt=0;not_no=0;not_nt=0;  
        
        //////////////////////////////////////////////////        
        
        if(nl_PERSON.getLength()>0 && nl_PERSON != null) {
            for(int f=0; f<nl_PERSON.getLength() ; f++){
                 N_P = nl_PERSON.item(f);
                 N_P_E = (Element)N_P;
                if( ( GuessPersonID == Integer.parseInt(N_P_E.getAttribute("PersonID"))) ){
                    PersonCount_in_Document++; 
                    np++;
                    flag_coExis_P = 1;
                    entConcentration_P += t_entConcentration_P;
                    t_entConcentration_P = 0; 
                } else{
                    t_entConcentration_P++;
                    not_np++;
                }                              
            }                        
        }
        if(nl_ORG.getLength()>0 && nl_ORG != null) {
            for(int f=0; f<nl_ORG.getLength() ; f++){
                 N_O = nl_ORG.item(f);
                 N_O_E = (Element)N_O;
                if( ( GuessOrganizationID == Integer.parseInt(N_O_E.getAttribute("OrganizationID"))) ){
                    OrgCount_in_Document++; 
                    no++;
                    flag_coExis_O = 1;
                    entConcentration_O += t_entConcentration_O;
                    t_entConcentration_O = 0; 
                } else{
                    t_entConcentration_O++;
                    not_no++;
                }                          
                }                               
            }                        
        
        
        if(nl_LOCATION.getLength()>0 && nl_LOCATION != null) {
            for(int f=0; f<nl_LOCATION.getLength() ; f++){
                 N_l = nl_LOCATION.item(f);
                 N_l_E = (Element)N_l;
                if( ( GuessLocationID == Integer.parseInt(N_l_E.getAttribute("LocationID"))) ){
                    LocationCount_in_Document++;
                    nl++;
                    flag_coExis_L = 1;
                    entConcentration_L += t_entConcentration_L;
                    t_entConcentration_L = 0; 
                } else{
                    t_entConcentration_L++;
                    not_nl++;
                }                     
                                              
            }                        
        }
        
        if(nl_TIME.getLength()>0 && nl_TIME != null) {
            for(int f=0; f<nl_TIME.getLength() ; f++){
                 N_T = nl_TIME.item(f);
                 N_T_E = (Element)N_T;
                if( ( GuessTimeID == Integer.parseInt(N_T_E.getAttribute("TimeID"))) ){
                    TimeCount_in_Document++;
                    nt++;
                    flag_coExis_T = 1;
                    entConcentration_T += t_entConcentration_T;
                    t_entConcentration_T = 0; 
                } else{
                    t_entConcentration_T++;
                    not_nt++;
                } 
                                               
            }                        
        }
        
        if( flag_coExis_L == 1 ){
            if(flag_coExis_P==1){
               coExis_PL++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OL++;
            }
            
        }
        if( flag_coExis_T == 1 ){
            if(flag_coExis_P==1){
               coExis_PT++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OT++;
            }
            
        }
        if( flag_coExis_T == 1 && flag_coExis_L == 1 ){
            if(flag_coExis_P==1){
               coExis_PLT++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OLT++;
            }
            coExis_LT++;
        }   
        
        
        
        
        //////////////////////QUOTATION CASE///////////////////////////
       
        NodeList nlsp_nl_s_0_E  =  nl_s_0_E.getElementsByTagName("speaker");
        Node speaker;
        Element spk_E;
        int QuotespeakID_P = -100,  QuotespeakID_O = -100;
        if(nlsp_nl_s_0_E.getLength()>0 && nlsp_nl_s_0_E != null ){            
            for(int sss=0; sss<nlsp_nl_s_0_E.getLength() ; sss++){
             speaker = nlsp_nl_s_0_E.item(sss);
             spk_E = (Element)speaker;
             
            NodeList nl_spk_E_P =   spk_E.getElementsByTagName("PERSON");      
            /*Person as speaker*/
            if(nl_spk_E_P!=null)
            for(int ssss=0; ssss<nl_spk_E_P.getLength() ; ssss++){
                Node nl_spk_E_0 = nl_spk_E_P.item(ssss);
                Element nl_spk_E_0_E = (Element)nl_spk_E_0;
                
                 QuotespeakID_P = Integer.parseInt(nl_spk_E_0_E.getAttribute("PersonID"));              
               
                if( QuotespeakID_P == GuessPersonID ){              
                    np++; 
                    PersonCount_in_Document++;
                    this.quotation_PL = ( 5.0*( np+nl ) - 2.0*( not_nl + not_np )  );
                    this.quotation_PT = ( 5.0*( np+nt ) - 2.0*( not_nt + not_np )  );
                }else{
                    not_np++;
                    this.quotation_PL = ( 1.0*( np+nl ) - 1.0*( not_nl + not_np )  );
                    this.quotation_PT = ( 1.0*( np+nt ) - 1.0*( not_nt + not_np )  );
                }                                
            }
            /*Person as speaker*/
            
            /*Organizatio as speaker*/
            NodeList nl_spk_E_O =   spk_E.getElementsByTagName("Organization");
            if(nl_spk_E_O!=null)
            for(int ssss=0; ssss<nl_spk_E_O.getLength() ; ssss++){
                Node nl_spk_E_0 = nl_spk_E_O.item(ssss);
                Element nl_spk_E_0_E = (Element)nl_spk_E_0;
                
                 QuotespeakID_O = Integer.parseInt(nl_spk_E_0_E.getAttribute("OrganizationID"));                

                if( QuotespeakID_O == GuessOrganizationID ){              
                    no++; 
                    OrgCount_in_Document++;
                    this.quotation_OL += ( 5.0*( no+nl ) - 2.0*( not_nl + not_no )  );
                    this.quotation_OT += ( 5.0*( no+nt ) - 2.0*( not_nt + not_no )  );
                }else{
                    not_no++;
                    this.quotation_OL += ( 1.0*( no+nl ) - 1.0*( not_nl + not_no )  );
                    this.quotation_OT += ( 1.0*( no+nt ) - 1.0*( not_nt + not_no )  );
                } 
            }            
            /*Organizatio as speaker*/
            
        }                

        }


    }
 
/* text Processing*/
    }
    /* title Processing*/
    public void titleProcessing(){  

        
            /* text Processing*/
            int t_entConcentration_P=0,t_entConcentration_T=0,t_entConcentration_L=0,t_entConcentration_O=0;// temp for entConc
             NodeList nl_text = docEle.getElementsByTagName("Title");
             Node textNode = nl_text.item(0);
             Element textNode_E = (Element)textNode;
             NodeList nl_s = textNode_E.getElementsByTagName("s");
             totalSentence = nl_s.getLength();
 ////////////////////////////////// think down to single sentence ////////////////////////////////////
    for(int ss=0; ss<nl_s.getLength() ; ss++){ 
        Node nl_s_0 = nl_s.item(ss);
        Element nl_s_0_E = (Element)nl_s_0;
        
        //////////////////////////////////////////////////
        NodeList nl_PERSON  =  nl_s_0_E.getElementsByTagName("PERSON");
        NodeList nl_ORG  =  nl_s_0_E.getElementsByTagName("Organization");
        NodeList nl_LOCATION  =  nl_s_0_E.getElementsByTagName("LOCATION");
        NodeList nl_TIME  =  nl_s_0_E.getElementsByTagName("TIME");        
        Node  N_P,N_l, N_T, N_O;
        Element N_P_E,N_l_E, N_T_E, N_O_E;  
        int flag_coExis_P = 0,flag_coExis_L = 0, flag_coExis_T =0, flag_coExis_O =0  ;  
        
        np=0;nl=0;not_np=0;not_nl=0;    
        no=0;nt=0;not_no=0;not_nt=0;  
        
        //////////////////////////////////////////////////        
        
        if(nl_PERSON.getLength()>0 && nl_PERSON != null) {
            for(int f=0; f<nl_PERSON.getLength() ; f++){
                 N_P = nl_PERSON.item(f);
                 N_P_E = (Element)N_P;
                if( ( GuessPersonID == Integer.parseInt(N_P_E.getAttribute("PersonID"))) ){
                    PersonCount_in_Document++; 
                    np++;
                    this.title_match_P = 5;
                    flag_coExis_P = 1;
                    entConcentration_P += t_entConcentration_P;
                    t_entConcentration_P = 0; 
                } else{
                    t_entConcentration_P++;
                    not_np++;
                }                              
            }                        
        }
        if(nl_ORG.getLength()>0 && nl_ORG != null) {
            for(int f=0; f<nl_ORG.getLength() ; f++){
                 N_O = nl_ORG.item(f);
                 N_O_E = (Element)N_O;
                if( ( GuessOrganizationID == Integer.parseInt(N_O_E.getAttribute("OrganizationID"))) ){
                    OrgCount_in_Document++; 
                    no++;
                    flag_coExis_O = 1;
                    this.title_match_O = 5;
                    entConcentration_O += t_entConcentration_O;
                    t_entConcentration_O = 0; 
                } else{
                    t_entConcentration_O++;
                    not_no++;
                }                          
                }                               
            }                        
        
        
        if(nl_LOCATION.getLength()>0 && nl_LOCATION != null) {
            for(int f=0; f<nl_LOCATION.getLength() ; f++){
                 N_l = nl_LOCATION.item(f);
                 N_l_E = (Element)N_l;
                if( ( GuessLocationID == Integer.parseInt(N_l_E.getAttribute("LocationID"))) ){
                    LocationCount_in_Document++;
                    nl++;
                    flag_coExis_L = 1;
                    this.title_match_L = 5;
                    entConcentration_L += t_entConcentration_L;
                    t_entConcentration_L = 0; 
                } else{
                    t_entConcentration_L++;
                    not_nl++;
                }                     
                                              
            }                        
        }
        
        if(nl_TIME.getLength()>0 && nl_TIME != null) {
            for(int f=0; f<nl_TIME.getLength() ; f++){
                 N_T = nl_TIME.item(f);
                 N_T_E = (Element)N_T;
                if( ( GuessTimeID == Integer.parseInt(N_T_E.getAttribute("TimeID"))) ){
                    TimeCount_in_Document++;
                    nt++;
                    flag_coExis_T = 1;
                    this.title_match_T = 5;
                    entConcentration_T += t_entConcentration_T;
                    t_entConcentration_T = 0; 
                } else{
                    t_entConcentration_T++;
                    not_nt++;
                } 
                                               
            }                        
        }
        
        if( flag_coExis_L == 1 ){
            if(flag_coExis_P==1){
               coExis_PL++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OL++;
            }
            
        }
        if( flag_coExis_T == 1 ){
            if(flag_coExis_P==1){
               coExis_PT++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OT++;
            }
            
        }
        if( flag_coExis_T == 1 && flag_coExis_L == 1 ){
            if(flag_coExis_P==1){
               coExis_PLT++; 
            }
            else if( flag_coExis_O==1 ){
                coExis_OLT++;
            }
            coExis_LT++;
        }   
        
        
        
        
        //////////////////////QUOTATION CASE///////////////////////////
       
        NodeList nlsp_nl_s_0_E  =  nl_s_0_E.getElementsByTagName("speaker");
        Node speaker;
        Element spk_E;
        int QuotespeakID_P = -100,  QuotespeakID_O = -100;
        if(nlsp_nl_s_0_E.getLength()>0 && nlsp_nl_s_0_E != null ){            
            for(int sss=0; sss<nlsp_nl_s_0_E.getLength() ; sss++){
             speaker = nlsp_nl_s_0_E.item(sss);
             spk_E = (Element)speaker;
             
            NodeList nl_spk_E_P =   spk_E.getElementsByTagName("PERSON");      
            /*Person as speaker*/
            if(nl_spk_E_P!=null)
            for(int ssss=0; ssss<nl_spk_E_P.getLength() ; ssss++){
                Node nl_spk_E_0 = nl_spk_E_P.item(ssss);
                Element nl_spk_E_0_E = (Element)nl_spk_E_0;
                
                 QuotespeakID_P = Integer.parseInt(nl_spk_E_0_E.getAttribute("PersonID"));              
               
                if( QuotespeakID_P == GuessPersonID ){              
                    np++; 
                    PersonCount_in_Document++;
                    this.quotation_PL = ( 5.0*( np+nl ) - 2.0*( not_nl + not_np )  );
                    this.quotation_PT = ( 5.0*( np+nt ) - 2.0*( not_nt + not_np )  );
                }else{
                    not_np++;
                    this.quotation_PL = ( 1.0*( np+nl ) - 1.0*( not_nl + not_np )  );
                    this.quotation_PT = ( 1.0*( np+nt ) - 1.0*( not_nt + not_np )  );
                }                                
            }
            /*Person as speaker*/
            
            /*Organizatio as speaker*/
            NodeList nl_spk_E_O =   spk_E.getElementsByTagName("Organization");
            if(nl_spk_E_O!=null)
            for(int ssss=0; ssss<nl_spk_E_O.getLength() ; ssss++){
                Node nl_spk_E_0 = nl_spk_E_O.item(ssss);
                Element nl_spk_E_0_E = (Element)nl_spk_E_0;
                
                 QuotespeakID_O = Integer.parseInt(nl_spk_E_0_E.getAttribute("OrganizationID"));                

                if( QuotespeakID_O == GuessOrganizationID ){              
                    no++; 
                    OrgCount_in_Document++;
                    this.quotation_OL += ( 5.0*( no+nl ) - 2.0*( not_nl + not_no )  );
                    this.quotation_OT += ( 5.0*( no+nt ) - 2.0*( not_nt + not_no )  );
                }else{
                    not_no++;
                    this.quotation_OL += ( 1.0*( no+nl ) - 1.0*( not_nl + not_no )  );
                    this.quotation_OT += ( 1.0*( no+nt ) - 1.0*( not_nt + not_no )  );
                } 
            }            
            /*Organizatio as speaker*/
            
        }                

        }


    }

    }      
    
      /* title Processing*/
    public myDocument(File file) {
        
        try {
        this.Preparation(file);  
        this.ProcessLabel();
        this.GuessProcessing();
        this.textProcessing();
        this.titleProcessing();
        
        }catch(Exception e) { e.printStackTrace(); }
                
    }
    
} 
    
