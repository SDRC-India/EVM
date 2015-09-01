package org.sdrc.evm.web;

import org.sdrc.evm.model.LandingModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LandingController {
	
	@RequestMapping(value = "/landing", method = RequestMethod.GET)
	public String redirectLandingPage(@RequestParam(required=false) String feature, RedirectAttributes redirectAttributes) throws Exception {
		System.out.println("Feature name:-->"+feature);
		if(feature == null || feature.equals(""))
			redirectAttributes.addFlashAttribute("feature", "Dashboard");
		else
			redirectAttributes.addFlashAttribute("feature", feature);
		
		return "redirect:/evmlanding";
	}

	@RequestMapping(value = "/landingredirect" , method = RequestMethod.POST )
	public String redirectPage(LandingModel lModel, RedirectAttributes redirectAttributes){
		System.out.println("====================>>>"+lModel.getFeatureName()+"<>"+lModel.getAreaName()+"<>"+lModel.getAreaCode());
		if(lModel.getFeatureName().equalsIgnoreCase("dashboard")){
			
			redirectAttributes.addFlashAttribute("areaId", lModel.getAreaCode());
			redirectAttributes.addFlashAttribute("areaName", lModel.getAreaName());
			return "redirect:/dashboard";
			
		}else if(lModel.getFeatureName().equalsIgnoreCase("factsheet")){
			redirectAttributes.addFlashAttribute("areaId", lModel.getAreaCode());
			redirectAttributes.addFlashAttribute("areaName", lModel.getAreaName());
			return "redirect:/factsheets";
			
		}else{
			return "redirect:/dashboard";
		}
	}
//	<mvc:view-controller path="/dashboard" view-name="dashboard" />
//	return rootURL + "/dashboard?" + "st=" + st + "&ss=" + ss + "&sss=" + sss
//			+ "&si=" + si + "&sgky=" + sgky + "&sgval=" + sgval + "&sgky1="
//			+ sgky1 + "&sgval1=" + sgval1 + "&sst=" + sst;
	@RequestMapping(value = "/dashboard" , method = RequestMethod.GET )
	public String getDashboard(Model model,
			@RequestParam(required=false) String st,@RequestParam(required=false) String pa,
			@RequestParam(required=false) String ss,@RequestParam(required=false) String sss,
			@RequestParam(required=false) String si,@RequestParam(required=false) String sgky,
			@RequestParam(required=false) String sgval,@RequestParam(required=false) String sgky1,
			@RequestParam(required=false) String sgval1,@RequestParam(required=false) String sst,
			RedirectAttributes redirectAttributes){
		
		if( st == null || st.equals("")  ){
			String areaId = (String)model.asMap().get("areaId");
			String areaName = (String)model.asMap().get("areaName");
			if(areaId==null || areaId.equals("")){
				return "redirect:/landing";
			}else{
				return "dashboard";
			}
		}else{
			System.out.println(pa + " " + st + " " + ss + " " + sss + " " + si + " " + sgky + " "  + sgval + " "  + sgky1 + " "  + sgval1 + " "  + sst );
			redirectAttributes.addFlashAttribute("areaId", pa);
			redirectAttributes.addFlashAttribute("areaName", pa);
			redirectAttributes.addFlashAttribute("st", st);
			redirectAttributes.addFlashAttribute("ss", ss);
			redirectAttributes.addFlashAttribute("sss", sss);
			redirectAttributes.addFlashAttribute("si", si);
			redirectAttributes.addFlashAttribute("sgky", sgky);
			redirectAttributes.addFlashAttribute("sgval", sgval);
			redirectAttributes.addFlashAttribute("sgky1", sgky1);
			redirectAttributes.addFlashAttribute("sgval1", sgval1);
			redirectAttributes.addFlashAttribute("sst", sst);
			return "redirect:/dashboard";
			
		}
		
	}
//	<mvc:view-controller path="/evmlanding" view-name="evmlanding" />
	@RequestMapping(value = "/evmlanding" , method = RequestMethod.GET )
	public String getLandingPage(Model model){
		
		String featureName = (String)model.asMap().get("feature");
		if(featureName==null || featureName.equals("")){
			return "redirect:/landing";
		}else{
			return "evmlanding";
		}
	}
}
