package org.sdrc.evm.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sdrc.devinfo.repository.UtAreaEnRepository;
import org.sdrc.devinfo.repository.UtDataRepository;
import org.sdrc.devinfo.repository.UtIndicatorUnitSubgroupRepository;
import org.sdrc.devinfo.repository.UtTimeperiodRepository;
import org.sdrc.evm.repository.EvmRequirementRepository;
import org.sdrc.evm.repository.XFormRepository;
import org.sdrc.odkaggregate.domain.EvmQuestion;
import org.sdrc.odkaggregate.domain.EvmRequirement;
import org.sdrc.odkaggregate.domain.EvmSubQuestion;
import org.sdrc.odkaggregate.domain.XForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssamAggregationServiceImpl implements AggregateService {

	private static final Logger LOGGER = Logger
			.getLogger(AggregationServiceImpl.class);

	@Autowired
	private AggregationServiceUtility aggregationServiceUtility;
	
	@Autowired
	private UtAreaEnRepository areaEnRepository;

	@Autowired
	private UtIndicatorUnitSubgroupRepository indicatorUnitSubgroupRepository;

	@Autowired
	private UtDataRepository utDataRepository;
	
	private static final DecimalFormat df = new DecimalFormat("#.#");

	@Autowired
	private ResourceBundleMessageSource aggregationInfoMessageSource;

	@Autowired
	private UtTimeperiodRepository timeperiodRepository;
	
	@Autowired
	private XFormRepository xFormRepository;
	
	@Autowired
	private EvmRequirementRepository evmRequirementRepository;




	@Override
	@Transactional
	public void AssamdoAggregateByForm() {

		// get all forms from Database.
		// iterate all forms
		// for each form get xml definition,
		// get submissions of the form,
		// parse submission to get data using database xpath configuration.
		List<XForm> forms=xFormRepository.getAll();
		for(XForm form:forms){
			System.out.println(form.getForm_id());
			
//			if(!form.getForm_id().equals("EVM_HealthFacility_04082014_V1")){
			if(form.getForm_id().equals("EVM_SVS_RVS_09092014_V4")){
				aggregationServiceUtility.ProcessXform(form);
			}
		}

	}


	@Override
	@Transactional
	public void insertAggregateDetails() {
		
		HashMap<String, List<String>> svsE1 = new HashMap<String, List<String>>() {
            {
            	put("E1_1.2#Boolean#5#wt#wt#M", Arrays.asList("E1_02b_A","E1_02b_B","E1_02b_C","E1_02b_D","E1_02b_E","E1_02b_F","E1_02b_G"));
            	
            	put("E1_1.3#calE1_03#5#wt#wt#M", Arrays.asList("E1_03b_A","E1_03b_B","E1_03b_C","E1_03b_D"));
            	
            	put("E1_1.4#calE1_05#1#wt#wt#M", Arrays.asList("E1_04a_A","E1_04a_B"));
            	
            	put("E1_1.5#calE1_05#1#wt#wt#M", Arrays.asList("E1_05a_A","E1_05a_B"));
            	
            	put("E1_1.6#calE1_06#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M", Arrays.asList("E1_06b_A","E1_06b_B","E1_06b_C","E1_06b_D"));
            	
            	put("E1_1.7#EvenByOdd#1#eqy#E1/E1_1.1/E1_01a_E#M", Arrays.asList("E1_07a_A","E1_07a_B"));
            	
            	put("E1_1.8#EvenByOdd#1#eqy#E1/E1_1.1/E1_01a_D#M", Arrays.asList("E1_08a_A","E1_08a_B"));
            	
            	put("E1_1.9#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M", Arrays.asList("E1_09a_A","E1_09a_B","E1_09a_C","E1_09a_D"));
            	
            	put("E1_1.10#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#T", Arrays.asList("E1_10a"));
            	
            	put("E1_1.11#Boolean#5#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M", Arrays.asList("E1_11a"));
            	
            	put("E1_1.13#Boolean#1#eqy#E1/E1_1.12/E1_12a#M", Arrays.asList("E1_13a_A","E1_13a_B","E1_13a_C","E1_13a_D","E1_13a_E"));
            	
            	put("E1_1.14#Boolean#1#eqory#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#V", Arrays.asList("E1_14a"));
            	
            	put("E1_1.17#Boolean#1#eqory#E1/E1_1.15/E1_15a,E1/E1_1.16/E1_16a_A, E1/E1_1.16/E1_16a_B, E1/E1_1.16/E1_16a_C#M", Arrays.asList("E1_17a_A","E1_17a_B","E1_17a_C","E1_17a_D"));
            	
            	put("E1_1.18#Boolean#5#eqy#E1/E1_1.15/E1_15a#M", Arrays.asList("E1_18b_A","E1_18b_B","E1_18b_C","E1_18b_D","E1_18b_E","E1_18b_F"));
            	
            	put("E1_1.19#calE1_19#5#eqy#E1/E1_1.15/E1_15a#M", Arrays.asList("E1_19a_A","E1_19a_B","E1_19a_C"));

            }};
            
            HashMap<String, List<String>> svsE2 = new HashMap<String, List<String>>() {
    			{
    				put("E2_2.2.1c#Range_0_4#5#wt#wt#M", Arrays.asList("E2_01a_A","E2_01a_B"));
    				
    				put("E2_2.2.1d#EvenByOdd#1#eqy#E0_01/group_q1/ E0_01a_A#M", Arrays.asList("E2_02a_A","E2_02a_B"));
    				
    				put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));
    				
    				put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));
    				
    				put("E2_2.2.1b#Boolean#5#wt#wt#T", Arrays.asList("E2_06a"));
    				
    				put("E2_2.2.3#Boolean#5#wt#wt#M", Arrays.asList("E2_07b_B","E2_07b_C"));
    				
    				put("E2.08A#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#M", Arrays.asList("E2_08a"));
    				
    				put("E2_2.09A#Boolean#5#eqy#E0_01/group_q3/E0_03a_B,E0_01/group_q4/E0_04a_A#M", Arrays.asList("E2_09a"));
    				
    				put("E2_2.10A#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#M", Arrays.asList("E2_10a"));
    				
    				put("E2_2.2.4#Boolean#1#wt#wt#M", Arrays.asList("E2_11a"));
    				
    				put("E2_2.2.5#Boolean#5#wt#wt#M", Arrays.asList("E2_12a_A","E2_12a_B"));
    				
    				put("E2_2.3.1#Boolean#1#eqory#E0_01/group_q1/E0_01a_A,E0_01/group_q3/E0_03a_B#M", Arrays.asList("E2_13b_A","E2_13b_B"));
    			}};
    			
    	 HashMap<String, List<String> > svsE3 = new HashMap<String, List<String>>(){{
    		 
    		 put("E3_3.1.1#netStorage#5#wt#wt#C", Arrays.asList("E3_01a_A","estimated_cal_1/E3_01a_B"));
    		 
    		 put("E3_3.02#netStorage#5#multi#E0_01/group_q1/E0_01a_A,E0_01/group_q1/E0_01a_C,E3/E3_3.02/E3_02a_A#C", Arrays.asList("E3_02a_B","estimated1_cal/E3_02a_C"));
    		 
    		 put("E3_3.1.2#netStorage#5#eqymin#E0_01/group_q2/E0_02a_A#C", Arrays.asList("E3_03a_A","estimated1_cal_1/E3_03a_B"));
    		 
    		 put("E3_3.3.1#netStorage#5#eqy#E0_01/group_q4/E0_04a_A#C", Arrays.asList("E3_08a_A","E3_08a_B"));
    		 
    		 put("E3_3.4.1#calE3_09#5#multi#E0_01/group_q3/E0_03a_A,E3/E3_3.4.1/E3_09a_A,E3/E3_3.4.1/E3_09a_D#C", Arrays.asList("E3_09a_A","E3_09a_B","E3_09a_C","E3_09a_D","E3_09a_E","E3_09a_F"));
    		 
    		 put("E3_3.4.2#netStorage#5#eqy#E0_01/group_q3/E0_03a_A,E3/E3_3.4.2/ E3_10a_A#C", Arrays.asList("E3_10a_B","E3_10a_C"));
    		 
    		 put("E3_3.5.1#Boolean#5#wt#wt#M", Arrays.asList("E3.11a_A","E3.11a_B","E3.11a_C"));
    	 }};

	 HashMap<String, List<String> > svsE4 = new HashMap<String, List<String>>(){{
	    	
         put("E4_4.1.1#Boolean#5#wt#wt#B",Arrays.asList("E4_01a_A","E4_01a_B"));
         
         put("E4_4.02#e4_02_Exception#1#eqy#E0_01/group_q2/E0_02a_D#B",Arrays.asList("E4_02a_A","E4_02a_B"));
         
         put("E4_4.1.2#Boolean#5#wt#wt#B",Arrays.asList("E4_03a_A","E4_03a_B","E4_03a_C","E4_03a_D","E4_03a_E","E4_03a_F","E4_03a_G","E4_03a_H","E4_03a_J","E4_03a_K"));
        
         put("E4_4.1.3#Boolean#1#eqory#E0_01/group_q2/E0_02a_C,E0_01/group_q2/E0_02a_D#B",Arrays.asList("E4_04a_A","E4_04a_B","E4_04a_C","E4_04a_D","E4_04a_E","E4_04a_F","E4_04a_G","E4_04a_H","E4_04a_J","E4_04a_K"));
        
         put("E4_4.2.1#Boolean#5#wt#wt#B",Arrays.asList("E4_05a_A","E4_05a_B"));
        
         put("E4_4.2.2#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#B",Arrays.asList("E4_06a_A","E4_06a_B","E4_06a_C","E4_06a_D","E4_06a_E","E4_06a_F","E4_06a_G","E4_06a_H","E4_06a_J"));
        
         put("E4_4.07#Boolean#5#eqy#E0_01/group_q3/E0_03a_B#B",Arrays.asList("E4_07a_A","E4_07a_B","E4_07a_C","E4_07a_D","E4_07a_E","E4_07a_F","E4_07a_G"));
        
         put("E4_4.08#Boolean#5#wt#wt#B",Arrays.asList("E4_08a_A","E4_08a_B"));
        
         put("E4_4.2.3#Boolean#1#wt#wt#B",Arrays.asList("E4_09a_A","E4_09a_B","E4_09a_C","E4_09a_D"));
         
         put("E4_4.2.4#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#B",Arrays.asList("E4_10b_A","E4_10b_B"));
         
         put("E4_4.3.1#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#E",Arrays.asList("E4_11b_A","E4_11b_B","E4_11b_C","E4_11b_D","E4_11b_E","E4_11b_F"));
         
         put("E4_4.3.2#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#E",Arrays.asList("E4_12a_A","E4_12a_B"));
         
         put("E4_4.3.3#Boolean#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/E0_01a_C#E",Arrays.asList("E4_13b_A","E4_13b_B","E4_13b_C","E4_13b_D","E4_13b_E","E4_13b_F"));
        
         put("E4_4.3.4#EvenByOdd#1#wt#wt#E",Arrays.asList("E4_14a_A","E4_14a_B"));
        
         put("E4_4.3.5#Boolean#5#noty#E4_4.1/E4_4.3.5/E4_15a_A#E",Arrays.asList("E4_15a_B"));
        
         put("E4_4.16#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A,E4_4.1/E4_4.3.5/E4_15a_B#E",Arrays.asList("E4_16a_A","E4_16a_B","E4_16a_C","E4_16a_D","E4_16a_E", "E4_16a_F"));
        
         put("E4_4.18#EvenByOdd#1#noty#E4_4.1/E4_4.3.6/E4_17a#E",Arrays.asList("E4_18a_A","E4_18a_B","E4_18a_C","E4_18a_D","E4_18a_E","E4_18a_F"));
        
         put("E4_4.3.7#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#E",Arrays.asList("E4_19b_A","E4_19b_B"));
        
         put("E4_4.3.9#Boolean#5#eqy#E0_01/group_q1/E0_01a_C#E",Arrays.asList("E4_20b_A","E4_20b_B"));
        
         put("E4_4.3.8#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",Arrays.asList("E4_21b_A", "E4_21b_B" ));
         
         put("E4_4.4.1#mulWeightDivide2#5#eqy#E0_01/group_q1/E0_01a_A#E",Arrays.asList("E4_22b_A","E4_22b_B","E4_22b_C", "E4_22b_D","E4_22b_E","E4_22b_F"));
        
         put("E4_4.23#mulWeightDivide#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/E0_01a_C#E",Arrays.asList("E4_23b_A","E4_23b_B","E4_23b_C"));
        
         put("E4_4.4.2#Boolean#5#wt#wt#E",Arrays.asList("E4_24b"));
         
         put("E4_4.4.3#Boolean#5#eqy#E0_01/group_q4/E0_04a_A#V",Arrays.asList("E4_25a_A","E4_25a_B","E4_25a_C", "E4_25a_D"));
         
         put("E4_4.4.4#Boolean#5#eqy#E0_01/group_q3/E0_03a_B,E0_01/group_q4/E0_04a_A#V",Arrays.asList("E4_26a_A", "E4_26a_B","E4_26a_C","E4_26a_D","E4_26a_E","E4_26a_F","E4_26a_G","E4_26a_H", "E4_26a_J" ));
         
         put("E4_4.4.5#calculateE4_27#1#multi#E0_01/group_q3/E0_03a_A,E0_01/group_q3/E0_03a_B,E0_01/group_q4/E0_04a_A#E",Arrays.asList("E4_27a_A","E4_27a_B"));
         
         
     }};
     
     HashMap<String, List<String> > svsE5 = new HashMap<String, List<String>>(){{
	    	
	        put("E5_5.1.1#Boolean#5#wt#wt#R",Arrays.asList("E5_01a_A","E5_01a_B","E5_01a_C"));
	        
	        put("E5_5.1.2#e5_2_exception#5#wt#wt#R",Arrays.asList("E5_02a_A","E5_02a_B","E5_02a_C","E5_02a_D","E5_02a_E","E5_02a_F","E5_02a_G"));
	        
	        put("E5_5.1.3#Boolean#1#eqy#E0_01/group_q4/E0_04a_A#R",Arrays.asList("E5_03a_A","E5_03a_B","E5_03a_C","E5_03a_D"));
	        
	        put("E5_5.04#multiplyWeight#5#eqy#E0_01/group_q1/E0_01a_A#R",Arrays.asList("E5_04b_A","E5_04b_B", "E5_04b_C"));
	        
	        put("E5_5.05#multiplyWeight#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/ E0_01a_C#R",Arrays.asList("E5_05b_A","E5_05b_B", "E5_05b_C"));
	        
	        put("E5_5.2.1#multiplyWeight#5#eqy#E0_01/group_q1/E0_01a_D#R",Arrays.asList("E5_06b_A","E5_06b_B", "E5_06b_C"));
	        
	        put("E5_5.2.2#E5Exception#1#multi#E0_01/group_q4/E0_04a_A,E0_01/group_q4/E0_04a_B#R",Arrays.asList("E5_07a_A","E5_07a_B"));
	    }};
     
	    HashMap<String, List<String> > svsE6 = new HashMap<String, List<String>>(){{
			 
	        put("E6_6.02#Boolean#5#eqy#E6_6.1/E6_6.1.1/E6_01a#E",Arrays.asList("E6_02a_A","E6_02a_B","E6_02a_C","E6_02a_D","E6_02a_E","E6_02a_F","E6_02a_G","E6_02a_H","E6_02a_J"));
	        
	        put("E6_6.1.2#Boolean#5#wt#wt#M",Arrays.asList("E6_03a"));
	        
	        put("E6_6.04#Boolean#5#wt#wt#M",Arrays.asList("E6_04a_A","E6_04a_B","E6_04a_C","E6_04a_D","E6_04a_E","E6_04a_F","E6_04a_G","E6_04a_H"));
	        
	        put("E6_6.1.3#Boolean#5#wt#wt#M",Arrays.asList("E6_05a_A","E6_05a_B","E6_05a_C","E6_05a_D","E6_05a_E","E6_05a_F","E6_05a_G"));
	        
	        put("E6_6.1.4#Boolean#1#wt#wt#M",Arrays.asList("E6_06a"));
	        
	        put("E6_6.07#Boolean#1#wt#wt#M",Arrays.asList("E6_07a"));
	        
	        put("E6_6.1.5#Boolean#1#wt#wt#M",Arrays.asList("E6_08b_A","E6_08b_B"));
	        
	        put("E6_6.1.6#Boolean#1#wt#wt#M",Arrays.asList("E6_09a"));
	        
	        put("E6_6.10#Boolean#1#wt#wt#M",Arrays.asList("E6_10a"));
	        
	        put("E6_6.1.7#Boolean#5#wt#wt#M",Arrays.asList("E6_11a"));
	        
	        put("E6_6.12#Boolean#5#wt#wt#M",Arrays.asList("E6_12a"));
	        
	        put("E6_6.1.8#Boolean#1#wt#wt#M",Arrays.asList("E6_13a"));
	        
	        put("E6_6.14#Boolean#1#wt#wt#M",Arrays.asList("E6_14a"));
	        
	        put("E6_6.1.9#Boolean#5#wt#wt#M",Arrays.asList("E6_15b_A","E6_15b_C","E6_15b_D"));
	        
	        put("E6_6.16#calculateE6_16#5#eqy#E6_6.1/E6_6.1.9/E6_15b_A#M",Arrays.asList("E6_16a_B","E6_16a_C"));
	        
	        put("E6_6.17#Boolean#1#wt#wt#M",Arrays.asList("E6_17a"));
	        
	        put("E6_6.18#Boolean#1#wt#wt#M",Arrays.asList("E6_18a"));
	        
	        put("E6_6.19#Boolean#5#wt#wt#M",Arrays.asList("E6_19a"));
	        
	        put("E6_6.2.1#Boolean#5#wt#wt#M",Arrays.asList("E6_20b_A","E6_20b_B","E6_20b_C","E6_20b_D","E6_20b_E", "E6_20b_F"));
	        
	        put("E6_6.2.2#calculateE6_21#5#wt#wt#M",Arrays.asList("E6_21a_A","E6_21a_B"));
	        
	        put("E6_6.3.1#calculateE6_22#5#wt#wt#M",Arrays.asList("E6_22a_A","E6_22a_B"));
	        
	        put("E6_6_23#calculateE6_23#5#wt#wt#M",Arrays.asList("E6_23b_G"));
	        
	        put("E6_6.3.2#calculateE6_23#1#wt##M",Arrays.asList("E6_24a_D"));
	        
	        put("E6_6.4.1#Boolean#1#wt#wt#M",Arrays.asList("E6_25a_A","E6_25a_B","E6_25a_C","E6_25a_D","E6_25a_E"));
	        
	        put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",Arrays.asList("E6_26a_A","E6_26a_B","E6_26a_C","E6_26a_D"));
	        
	        put("E6_6.27#Boolean#1#wt#wt#M",Arrays.asList("E6_27a"));
	        
	    }};
	    
	    HashMap<String, List<String> > svsE7 = new HashMap<String, List<String>>(){{
			 
	        put("E7_7.01#Boolean#5#wt#wt#M",Arrays.asList("E7_01a"));
	        
	        put("E7_7.02#calculateE7_2#1#wt#wt#M",Arrays.asList("E7_02b_A","E7_02b_B", "E7_02b_C"));
	        
	        put("E7_7.04#Boolean#1#wt#wt#M",Arrays.asList("E7_04a"));
	        
	        put("E7_7.2#calculateE7_5#1#wt#wt#M",Arrays.asList("E7_05b_A","E7_05b_B"));
	        
	        put("E7_7.3#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#T",Arrays.asList("E7_06a_A","E7_06a_B"));
	        
	        put("E7_7.07#Boolean#5#eqy#E0_01/group_q3/E0_03a_B,E0_01/group_q4/E0_04a_A#T",Arrays.asList("E7_07a_A","E7_07a_B","E7_07a_C"));
	        
	        put("E7_7.08#Boolean#5#multi#E7/E7_7.08/E7_08a#T",Arrays.asList("E7_08a"));
	        
	        put("E7_7.10#Range_0_4#5#eqy#E7/E7_7.4/E7_09a#M",Arrays.asList("E7_10a"));
	        
	        put("E7_7.11#E7_11#1#wt#wt#M",Arrays.asList("E7_11a_A","E7_11a_B","E7_11a_C1","E7_11a_C2","E7_11a_D"));
	        
	        put("E7_7.13#Boolean#5#eqy#E0_01/group_q4/E0_04a_A#M",Arrays.asList("E7_13a_A","E7_13a_B","E7_13a_C"));
	        
	        
	    }};
	    
	    HashMap<String, List<String> > svsE8 = new HashMap<String, List<String>>(){{
	    	
	    	put("E.8.01#Boolean#5#wt#wt#T", Arrays.asList("E8_01a_A","E8_01a_B","E8_01a_C"));
	    	
	    	put("E8_8.02#Boolean#1#eqna#E8/E8_8.02/E8_05a#M", Arrays.asList("E8_05a"));
	    	
	    	put("E8_8.03#Boolean#5#eqna#E8/E.8.03/E8_06a#T", Arrays.asList("E8_06a"));
	    	
	    	put("E8_8.04#Boolean#5#eqna#E8/E.8.04/E8_08a#M", Arrays.asList("E8_08a"));
	    	
	    	put("E8_8.05#Boolean#1#wt#wt#M", Arrays.asList("E8_12b"));
	    	
	    	put("E8_8.06#Boolean#5#wt#wt#M", Arrays.asList("E8_13b_A","E8_13b_B","E8_13b_C","E8_13b_D"));
	    	
	    	put("E8_8.07#Boolean#1#wt#wt#M", Arrays.asList("E8_16b_A","E8_16b_B"));

	    }};
	    
	    HashMap<String, List<String> > svsE9 = new HashMap<String, List<String>>(){{
	    
	    	put("E9_9.05#Boolean#5#eqy#E9/E9_9.01/E9_01a#M", Arrays.asList("E9_02b_A","E9_02b_B","E9_02b_C"));
	    	
	    	put("E9_9.06#Boolean#1#eqy#E9/E9_9.01/E9_01a#M", Arrays.asList("E9_03b_A","E9_03b_B","E9_03b_C"));
	    	
	    	put("E9_9.02#Boolean#1#eqy#E9/E9_9.01/E9_01a#M", Arrays.asList("E9_04b_A","E9_04b_B","E9_04b_C"));
	    	
	    	put("E9_9.03#Boolean#5#wt#wt#M", Arrays.asList("E9_07a_A","E9_07a_A_1/E9_07a_B","E9_07a_A_1/E9_07a_C","E9_07a_A_1/E9_07a_D"));
	    	
	    	put("E9_9.04#Boolean#5#wt#wt#M", Arrays.asList("E9_08a_A","E9_08a_B","E9_08a_C"));
	    	
	    	put("E9_9.08#Range_0_4#5#wt#wt#M", Arrays.asList("E9_10a_B"));
	    	
	    	put("E9_9.09#divideBy4#1#eqy#E0_01/group_q4/E0_04a_A#M", Arrays.asList("E9_11a_A","E9_11a_B"));
	    	
	    	put("E9_9.11#Boolean#1#eqy#E9/E9_9.10/E9_12a#M", Arrays.asList("E9_13a_A","E9_13a_B","E9_13a_C","E9_13a_D","E9_13a_E","E9_13a_F"));
	    	
	    	put("E9_9.13#Boolean#5#eqy#E9/E9_9.12/E9_14a_A#M", Arrays.asList("E9_15a_A","E9_15a_B","E9_15a_C","E9_15a_D"));
	    	
	    	put("E9_9.14#Boolean#5#eqy#E9/E9_9.12/E9_14a_B#M", Arrays.asList("E9_16a_A","E9_16a_B","E9_16a_C","E9_16a_D"));
	    	
	    	put("E9_9.15#Boolean#5#eqy#E9/E9_9.12/E9_14a_C#M", Arrays.asList("E9_17a_A","E9_17a_B","E9_17a_C","E9_17a_D"));
	    	
	    	put("E9_9.16#Boolean#5#eqy#E9/E9_9.12/E9_14a_D#M", Arrays.asList("E9_18a_A","E9_18a_B","E9_18a_C","E9_18a_D"));
	    	
	    	put("E9_9.17#calculateE9_20#5#wt#wt#M", Arrays.asList("E9_20a_A","E9_20a_B","E9_20a_C","E9_20a_D"));
	    	
	    	put("E9_9.18#Range_0_4#1#wt#wt#T", Arrays.asList("E9_21a"));
	    	
	    	put("E9_9.19#Range_0_4#1#wt#wt#T", Arrays.asList("E9_22a"));
	    	
	    	put("E9_9.20#Boolean#5#wt#wt#M", Arrays.asList("E9_23a_A","E9_23a_B"));
	    	
	    	put("E9_9.21#calculateE9_24#5#wt#wt#M", Arrays.asList("E9_24a_A","E9_24a_B"));
	    	
	    }};
	    
	    HashMap<String, List<String>> dvsE2 = new HashMap<String, List<String>>() {
			{
				
				put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));

				put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));

				put("E2_2.2.2#Boolean#5#wt#wt#T", Arrays.asList("E2_06a"));

				put("E2_2.2.3#Boolean#5#wt#wt#M", Arrays.asList("E2_07b_B","E2_07b_C"));
				
				put("E2_2.2.4#Boolean#1#wt#wt#M", Arrays.asList("E2_11a"));
				
				put("E2_2.2.5#Boolean#5#wt#wt#M", Arrays.asList("E2_12a_A", "E2_12a_B"));
				
			}};
			
		HashMap<String, List<String> > dvsE3 = new HashMap<String, List<String>>(){{
		    	
		    	put("E3_3.1.1#netStorage#5#wt#wt#C",Arrays.asList("E3_01a_A","estimated_cal_1/E3_01a_B"));
		        
		    	put("E3_3.1.2#netStorage#5#eqymin#E0_01/group_q2/E0_02a_A#C",Arrays.asList("E3_03a_A","E3_03a_B","E3_03a_C","E3_03a_D"));
		    	
		    	put("E3_3.3.1#netStorage#5#eqy#E0_01/group_q4/E0_04a_A#C",Arrays.asList("E3_08a_A","E3_08a_B"));
		    	
		    	put("E3_3.4.1#calE3_09#5#multi#E0_01/group_q3/E0_03a_A,E3_3.1/E3_3.4.1/E3_09a_A,E3_3.1/E3_3.4.1/E3_09a_D#C",Arrays.asList("E3_09a_A","E3_09a_B","E3_09a_C","E3_09a_D","E3_09a_E","E3_09a_F"));
		        
		        put("E3_3.4.2#netStorage#5#eqy#E0_01/group_q3/E0_03a_A,E3_3.1/E3_3.4.2/E3_10a_A#C",Arrays.asList("E3_10a_B","E3_10a_C"));
		        
		        put("E3_3.5.1#Boolean#5#wt#wt#M",Arrays.asList("E3.11a_A","E3.11a_B","E3.11a_C"));
		        
		    }};
		    
		    HashMap<String, List<String> > dvsE4 = new HashMap<String, List<String>>(){{
		    	
	 			put("E4_4.1.1#Boolean#5#wt#wt#B",Arrays.asList("E4_01a_A","E4_01a_B"));
	 			
	 			put("E4_4.1.1a#e4_02_Exception#1#eqy#E0_01/group_q2/E0_02a_D#B",Arrays.asList("E4_02a_A","E4_02a_B"));
	 			
	 			put("E4_4.1.2#Boolean#5#wt#wt#B",Arrays.asList("E4_03a_A","E4_03a_B","E4_03a_C","E4_03a_D","E4_03a_E","E4_03a_F","E4_03a_G","E4_03a_H","E4_03a_J","E4_03a_K"));
	           
	 			put("E4_4.2.2#Boolean#5#wt#wt#B",Arrays.asList("E4_08a_A","E4_08a_B"));
	 			  			
	            put("E4_4.3.3#Boolean#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/E0_01a_C#E",Arrays.asList("E4_13b_A","E4_13b_B","E4_13b_C","E4_13b_D","E4_13b_E","E4_13b_F"));
	           
	            put("E4_4.3.4#EvenByOdd#1#wt#wt#E",Arrays.asList("E4_14a_A","E4_14a_B"));
	           
	            put("E4_4.3.5#Boolean#5#noty#E4/E4_4.3.5/E4_15a_A#E",Arrays.asList("E4_15a_B"));
	            
	            put("E4_4.16#Boolean#5#eqy#E4/E4_4.3.5/E4_15a_A,E4/E4_4.3.5/E4_15a_B#E",Arrays.asList("E4_16a_A","E4_16a_B","E4_16a_C","E4_16a_D","E4_16a_E","E4_16a_F"));
	           
	            put("E4_4.18#EvenByOdd#1#noty#E4/E4_4.3.6/E4_17a#E",Arrays.asList("E4_18a_A","E4_18a_B","E4_18a_C","E4_18a_D","E4_18a_E","E4_18a_F"));
	           
	            put("E4_4.3.7#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",Arrays.asList("E4_21b_A","E4_21b_B"));
	            
	            put("E4_4.3.8#Boolean#5#wt#wt#E",Arrays.asList("E4_24b"));
	            
	            put("E4_4.4.1#Boolean#5#eqy#E0_01/group_q4/E0_04a_A#V",Arrays.asList("E4_25a_A","E4_25a_B","E4_25a_C","E4_25a_D"));
	            
	            put("E4_4.4.2#calculateE4_27#1#multi#E0_01/group_q3/E0_03a_A,E0_01/group_q3/E0_03a_B,E0_01/group_q4/E0_04a_A#E",Arrays.asList("E4_27a_A","E4_27a_B"));
	            
	        }};
	        
	        HashMap<String, List<String> > dvsE5 = new HashMap<String, List<String>>(){{
	        	
		        put("E5_5.1.1#Boolean#5#wt#wt#R",Arrays.asList("E5_01a_A","E5_01a_B","E5_01a_C"));
		        
		        put("E5_5.1.2#e5_2_exception#5#wt#wt#R",Arrays.asList("E5_02a_A","E5_02a_B","E5_02a_C","E5_02a_D","E5_02a_E","E5_02a_F","E5_02a_G"));
		        
		        put("E5_5.1.3#Boolean#1#eqy#E0_01/group_q4/E0_04a_A#R",Arrays.asList("E5_03a_A","E5_03a_B","E5_03a_C","E5_03a_D"));
		        
		        put("E5_5.2.1#multiplyWeight#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/ E0_01a_C#R",Arrays.asList("E5_05b_A","E5_05b_B","E5_05b_C"));
		        
		        put("E5_5.2.1a#multiplyWeight#5#eqy#E0_01/group_q1/E0_01a_D#R",Arrays.asList("E5_06b_A","E5_06b_B","E5_06b_C"));
		        
		        put("E5_5.2.2#E5Exception#1#multi#E0_01/group_q4/E0_04a_A,E0_01/group_q4/ E0_04a_B#R",Arrays.asList("E5_07a_A","E5_07a_B"));
		    }};
		    
		    HashMap<String, List<String> > dvsE6 = new HashMap<String, List<String>>(){{
		    	
		        put("E6_6.02#Boolean#5#eqy#E6_6.1/E6_6.1.1/E6_01a#E",Arrays.asList("E6_02a_A","E6_02a_B","E6_02a_C","E6_02a_D","E6_02a_E","E6_02a_F","E6_02a_G","E6_02a_H","E6_02a_J"));
		        
		        put("E6_6.1.2#Boolean#5#wt#wt#M",Arrays.asList("E6_03a"));
		        
		        put("E6_6.04#Boolean#5#wt#wt#M",Arrays.asList("E6_04a_A","E6_04a_B","E6_04a_C","E6_04a_D","E6_04a_E","E6_04a_F","E6_04a_G","E6_04a_H"));
		        
		        put("E6_6.1.3#Boolean#5#wt#wt#M",Arrays.asList("E6_05a_A","E6_05a_B","E6_05a_C","E6_05a_D","E6_05a_E","E6_05a_F","E6_05a_G"));
		        
		        put("E6_6.07#Boolean#1#wt#wt#M",Arrays.asList("E6_07a"));
		        
		        put("E6_6.1.5#Boolean#1#wt#wt#M",Arrays.asList("E6_08b_A","E6_08b_B"));
		        
		        put("E6_6.1.6#Boolean#1#wt#wt#M",Arrays.asList("E6_09a"));
		        
		        put("E6_6.10#Boolean#1#wt#wt#M",Arrays.asList("E6_10a"));
		        
		        put("E6_6.1.8#Boolean#1#wt#wt#M",Arrays.asList("E6_13a"));
		        
		        put("E6_6.14#Boolean#1#wt#wt#M",Arrays.asList("E6_14a"));
		        
		        put("E6_6.1.9#Boolean#5#wt#wt#M",Arrays.asList("E6_15b_A","E6_15b_C","E6_15b_D"));
		        
		        put("E6_6.16#calculateE6_16#5#eqy#E6_6.1/E6_6.1.9/E6_15b_A#M",Arrays.asList("E6_16a_B","E6_16a_C"));
		        
		        put("E6_6.17#Boolean#1#wt#wt#M",Arrays.asList("E6_17a"));
		        
		        put("E6_6.18#Boolean#1#wt#wt#M",Arrays.asList("E6_18a"));
		        
		        put("E6_6.19#Boolean#5#wt#wt#M",Arrays.asList("E6_19a"));
		        
		        put("E6_6.2.1#Boolean#5#wt#wt#M",Arrays.asList("E6_20b_A","E6_20b_B","E6_20b_C","E6_20b_D","E6_20b_E","E6_20b_F"));
		        
		        put("E6_6.2.2#calculateE6_21#5#wt#wt#M",Arrays.asList("E6_21a_A","E6_21a_B"));
		        
		        put("E6_6.3.1#calculateE6_22#5#wt#wt#M",Arrays.asList("E6_22a_A","E6_22a_B"));
		        
		        put("E6_6_23#calculateE6_23#5#wt#wt#M",Arrays.asList("E6_23b_G"));
		        
		        put("E6_6.3.2#calculateE6_23#1#wt##M",Arrays.asList("E6_24a_D"));
		        
		        put("E6_6.4.1#Boolean#1#wt#wt#M",Arrays.asList("E6_25a_A","E6_25a_B","E6_25a_C","E6_25a_D","E6_25a_E"));
		        
		        put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",Arrays.asList("E6_26a_A","E6_26a_B","E6_26a_C","E6_26a_D"));
		        
		        put("E6_6.27#Boolean#1#wt#wt#M",Arrays.asList("E6_27a"));
		        
		    }};
		    
		    HashMap<String, List<String> > dvsE7 = new HashMap<String, List<String>>(){{
			       
		    	put("E7_7.01#Boolean#5#wt#wt#M",Arrays.asList("E7_01a"));
		        
		        put("E7_7.02#calculateE7_2#1#wt#wt#M",Arrays.asList("E7_02b_A","E7_02b_B","E7_02b_C"));
		        
		        put("E7_7.1.2#Boolean#1#wt#wt#M",Arrays.asList("E7_04a"));
		        
		        put("E7_7.2.1#calculateE7_5#1#wt#wt#M",Arrays.asList("E7_05b_A","E7_05b_B"));
		        
		        put("E7_7.3.1#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#T",Arrays.asList("E7_06a_A","E7_06a_B"));
		        
		        put("E7_7.08#Boolean#5#multi#E7/E7_7.08/E7_08a#T",Arrays.asList("E7_08a"));
		        
		        put("E7_7.10#Range_0_4#5#eqy#E7/E7_7.4.1/E7_09a#M",Arrays.asList("E7_10a"));
		        
		        put("E7_7.11#E7_11#1#wt#wt#M",Arrays.asList("E7_11a_A","E7_11a_B","E7_11a_C1","E7_11a_C2","E7_11a_D"));
		        
		        put("E7_7.5.2#calculateE7_14#1#multi#E7/E7_7.5.2/E7_14a_A,E7/E7_7.5.2/E7_14a_B#M",Arrays.asList("E7_14a_B", "E7_14a_C"));
		    }};
	    
		    HashMap<String, List<String> > dvsE8 = new HashMap<String, List<String>>(){{
			       
		    	put("E.8.01#Boolean#5#wt#wt#T",Arrays.asList("E8_01a_A","E8_01a_B","E8_01a_C"));
		        
		        put("E8_8.02#Boolean#1#eqna#E8/E8_8.02/E8_05a#M",Arrays.asList("E8_05a"));
		        
		        put("E8_8.03#Boolean#5#eqna#E8/E8_8.03/E8_06a#T",Arrays.asList("E8_06a"));
		        
		        put("E8_8.03a#Boolean#5#eqna#E8/E8_8.03a/E8_08a#M",Arrays.asList("E8_08a"));
		        
		        put("E8_8.04#Boolean#1#wt#wt#M",Arrays.asList("E8_12b"));
		        
		        put("E8_8.05#Boolean#5#wt#wt#M",Arrays.asList("E8_13b_A","E8_13b_B","E8_13b_C","E8_13b_D"));
		        
		        
		    }};
		    
		    HashMap<String, List<String> > dvsE9 = new HashMap<String, List<String>>(){{
		    	
		        put("E9_9.02#Boolean#1#wt#wt#M",Arrays.asList("E9_04b_A","E9_04b_B","E9_04b_C"));
		        
		        put("E9_9.03#Boolean#5#wt#wt#M",Arrays.asList("E9_07a_A","E9_07a_A_1/E9_07a_B","E9_07a_A_1/E9_07a_C","E9_07a_A_1/E9_07a_D"));
		        
		        put("E9_9.03a#Boolean#5#wt#wt#M",Arrays.asList("E9_08a_A","E9_08a_B","E9_08a_C"));
		        
		        put("E9_9.3.3#Range_0_4#5#wt#wt#M",Arrays.asList("E9_10a_B"));
		        
		        put("E9_9.4.1a#Boolean#1#wt#wt#M",Arrays.asList("E9_13a_A","E9_13a_B","E9_13a_C","E9_13a_D","E9_13a_E","E9_13a_F"));
		        
		        put("E9_9.6.3#Boolean#5#wt#wt#M",Arrays.asList("E9_23a_A","E9_23a_B"));
		        
		        put("E9_9.6.4#calculateE9_24#5#wt#wt#M",Arrays.asList("E9_24a_A","E9_24a_B"));
		 
		        
		    }};
		    
		    
		    HashMap<String, List<String>> hfE2 = new HashMap<String, List<String>>() {
				{
                    put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));
                    
                    put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));
                    
                    put("E2_2.2.3_a#Boolean#5#wt#wt#T", Arrays.asList("E2_06a"));
                    
                    put("E2_2.2.3#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#M", Arrays.asList("E2_07b_B","E2_07b_C"));
                    
                    put("E2_2.2.4#Boolean#1#eqymin#E0_01/group_q1/E0_01a_E#M", Arrays.asList("E2_11a"));
                    
                    put("E2_2.2.5#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#M", Arrays.asList("E2_12a_A","E2_12a_B"));
										
				}};
				
			HashMap<String, List<String> > hfE3 = new HashMap<String, List<String>>(){{
				
                put("E3_3.1.1#netStorage#5#eqymin#E0_01/group_q1/E0_01a_E#C", Arrays.asList("E3_01a_A","estimated_cal_1/E3_01a_B"));

                put("E3_3.4.1#calE3_09#5#multi#E0_01/group_q3/E0_03a_A,E3/E3_3.4.1/E3_09a_A,E3/E3_3.4.1/E3_09a_D#C", Arrays.asList("E3_09a_A","E3_09a_B","E3_09a_C","E3_09a_D","E3_09a_E","E3_09a_F"));

                put("E3_3.4.2#netStorage#5#eqy#E0_01/group_q3/E0_03a_A,E3/E3_3.4.2/E3_10a_A#C", Arrays.asList("E3_10a_B","E3_10a_C"));

                put("E3_3.5.1#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#M", Arrays.asList("E3.11a_A","E3.11a_B","E3.11a_C"));
			
			}};
	    
			  HashMap<String, List<String> > hfE4 = new HashMap<String, List<String>>(){{
				  
                  put("E4_4.1.2#Boolean#5#wt#wt#B",Arrays.asList("E4_03a_A","E4_03a_B","E4_03a_C","E4_03a_D","E4_03a_E","E4_03a_F","E4_03a_G","E4_03a_H","E4_03a_J","E4_03a_K"));
                  
                  put("E4_4.3.3#Boolean#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/E0_01a_C#E",Arrays.asList("E4_13a_A","E4_13a_B","E4_13a_C","E4_13a_D","E4_13a_E","E4_13a_F"));
                  
                  put("E4_4.3.4#EvenByOdd#1#eqymin#E0_01/group_q1/E0_01a_E#E",Arrays.asList("E4_14a_A","E4_14a_B"));    
                  
                  put("E4_4.3.5#Boolean#5#noty#E4_4.1/E4_4.3.5/E4_15a_A#E",Arrays.asList("E4_15a_B"));
                  
                  put("E4_4.16#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A,E4_4.1/E4_4.3.5/E4_15a_B#E",Arrays.asList("E4_16a_A","E4_16a_B","E4_16a_C","E4_16a_D","E4_16a_E", "E4_16a_F"));
                  
                  put("E4_4.18#EvenByOdd#1#noty#E4_4.1/E4_4.3.6/E4_17a#E",Arrays.asList("E4_18a_A","E4_18a_B","E4_18a_C","E4_18a_D","E4_18a_E","E4_18a_F"));
                  
                  put("E_4.3.7#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",Arrays.asList("E4_21b_A", "E4_21b_B" ));
                  
                  put("E_4.3.8#Boolean#5#wt#wt#E",Arrays.asList("E4_24b"));
                  
                  put("E4_4.4.2#calculateE4_27#1#multi#E0_01/group_q3/E0_03a_A,E0_01/group_q3/E0_03a_B#E",Arrays.asList("E4_27a_A","E4_27a_B")); 
				  
			  }};
			  
			  HashMap<String, List<String> > hfE5 = new HashMap<String, List<String>>(){{
				  
                  put("E5_5.1.1#Boolean#5#wt#wt#R",Arrays.asList("E5_01a_A","E5_01a_B","E5_01a_C"));
                  
                  put("E5_5.1.2#e5_2_exception#5#eqymin#E0_01/group_q1/E0_01a_E#R",Arrays.asList("E5_02a_A","E5_02a_B","E5_02a_C","E5_02a_D","E5_02a_E","E5_02a_F","E5_02a_G"));

                  put("E5_5.05#multiplyWeight#5#noty#E0_01/group_q1/E0_01a_B,E0_01/group_q1/E0_01a_C#R",Arrays.asList("E5_05b_A","E5_05b_B", "E5_05b_C"));
                  
                  put("E5_5.06#multiplyWeight#5#eqy#E0_01/group_q1/E0_01a_D#R",Arrays.asList("E5_06b_A","E5_06b_B", "E5_06b_C"));  
				  
			  }};
			  
			   HashMap<String, List<String> > hfE6 = new HashMap<String, List<String>>(){{
				 
				   put("E6_6.1.2#Boolean#5#wt#wt#M",Arrays.asList("E6_03a"));
                   
                   put("E6_6.04#Boolean#5#wt#wt#M",Arrays.asList("E6_04a_A","E6_04a_B","E6_04a_C","E6_04a_D","E6_04a_E","E6_04a_F","E6_04a_G","E6_04a_H"));
                                  
                   put("E6_6.1.3#Boolean#5#wt#wt#M",Arrays.asList("E6_05a_A","E6_05a_B","E6_05a_C","E6_05a_D","E6_05a_E","E6_05a_F","E6_05a_G"));
                  
                   put("E6_6.07#Boolean#1#wt#wt#M",Arrays.asList("E6_07a"));
                                  
                   put("E6_6.1.5#Boolean#1#wt#wt#M",Arrays.asList("E6_08b_A","E6_08b_B"));
                  
                   put("E6_6.10#Boolean#1#wt#wt#M",Arrays.asList("E6_10a"));
                  
                   put("E6_6.1.9#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_15b_A","E6_15b_B","E6_15b_C","E6_15b_D"));
                                  
                   put("E6_6.16#calculateE6_16#5#multi#E0_01/group_q1/E0_01a_E,E6_6.1/E6_6.1.9/E6_15b_A#M",Arrays.asList("E6_16a_B","E6_16a_C"));
                   
                   put("E6_6.17#Boolean#1#wt#wt#M",Arrays.asList("E6_17a"));
                   
                   put("E6_6.18#Boolean#1#wt#wt#M",Arrays.asList("E6_18a"));
                   
                   put("E6_6.19#Boolean#5#wt#wt#M",Arrays.asList("E6_19a"));
                                  
                   put("E6_6.2.1#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_20b_A","E6_20b_B","E6_20b_C","E6_20b_D","E6_20b_E", "E6_20b_F"));
                                  
                   put("E6_6.2.2#calculateE6_21#5#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_21a_A","E6_21a_B"));
                                  
                   put("E6_6.3.1#calculateE6_22#5#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_22a_A","E6_22a_B"));
                                  
                   put("E6_6_23#calculateE6_23#5#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_23b_G"));
                                  
                   put("E6_6.3.2#calculateE6_23#1#eqymin#E0_01/group_q2/E0_02a_A#M",Arrays.asList("E6_24a_D"));
                                  
                   put("E6_6.4.1#Boolean#1#eqymin#E0_01/group_q1/E0_01a_E#M",Arrays.asList("E6_25a_A","E6_25a_B","E6_25a_C","E6_25a_D","E6_25a_E"));
                                  
                   put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",Arrays.asList("E6_26a_A","E6_26a_B","E6_26a_C","E6_26a_D"));
                                  
                   put("E6_6.27#Boolean#1#wt#wt#M",Arrays.asList("E6_27a"));
	   
			   }};
			   
			   HashMap<String, List<String> > hfE7 = new HashMap<String, List<String>>(){{
				   
                   put("E7_7.3#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#T",Arrays.asList("E7_06a_A","E7_06a_B"));
                   
                   put("E7_7.08#Boolean#5#multi#E7/E7_7.08/E7_08a#T",Arrays.asList("E7_08a"));
                                  
                   put("E7_7.10#Range_0_4#5#eqy#E7/E7_7.4.1/E7_09a#M",Arrays.asList("E7_10a"));
                                  
                   put("E7_7.5.2#calculateE7_14#1#multi#E7/E7_7.5.2/E7_14a_A,E7/E7_7.5.2/E7_14a_B#M",Arrays.asList("E7_14a_B","E7_14a_C"));

				   }};
				   
			HashMap<String, List<String>> hfE8 = new HashMap<String, List<String>>() {
						{
							
                            put("E.8.01#Boolean#5#eqymin#E0_01/group_q1/E0_01a_E#T", Arrays.asList("E8_01a_A","E8_01a_B","E8_01a_C"));
                            
                            put("E.8.02#Boolean#5#wt#wt#T", Arrays.asList("E8_02a"));
                            
                            put("E.8.03#Boolean#5#wt#wt#T", Arrays.asList("E8_03a"));
                            
                            put("E.8.04#Boolean#5#wt#wt#T", Arrays.asList("E8_04a"));
                            
                            put("E.8.05#Boolean#1#eqna#E8/E.8.05/E8_05a#M", Arrays.asList("E8_05a"));
                            
                            put("E.8.06#Boolean#5#eqna#E8/E.8.06/E8_06a#T", Arrays.asList("E8_06a"));
                            
                            put("E.8.07#Boolean#5#eqna#E8/E.8.07/E8_07a#M", Arrays.asList("E8_07a"));
                            
                            put("E.8.08#Boolean#5#eqna#E8/E.8.08/E8_08a#M", Arrays.asList("E8_08a"));
                            
                            put("E.8.10#Boolean#5#eqn#E8/E.8.09/E8_09a#T", Arrays.asList("E8_10a"));
                            
                            put("E.8.11#e8_11#5#eqy#E8/E.8.09/E8_09a,E8/E.8.11/E8_11b_A#M", Arrays.asList("E8_11b_B","E8_11b_C"));
                            
                            put("E.8.12#Boolean#1#wt#wt#M", Arrays.asList("E8_12b"));
                            
                            put("E.8.13#Boolean#5#wt#wt#M", Arrays.asList("E8_13b_A","E8_13b_B","E8_13b_C","E8_13b_D"));
                            
                            put("E.8.15#Boolean#5#wt#wt#M", Arrays.asList("E8_15b_A","E8_15b_B","E8_15b_C","E8_15b_D","E8_15b_E","E8_15b_F"));
							
						}};
			  
				 HashMap<String, List<String>> hfE9 = new HashMap<String, List<String>>() {
					{		
									
                        put("E9_9.3.1#Boolean#5#wt#wt#M", Arrays.asList("E9_07a_A","E9_07a_B","E9_07a_C","E9_07a_D"));
                        
                        put("E9_9.08#Boolean#5#wt#wt#M", Arrays.asList("E9_08a_A","E9_08a_B","E9_08a_C"));
                        
                        put("E9_9.6.3#Boolean#5#wt#wt#M", Arrays.asList("E9_23a_A","E9_23a_B"));
                        
                        put("E9_9.6.4#calculateE9_24#5#wt#wt#M", Arrays.asList("E9_24a_A","E9_24a_B"));
									
					}};
			
	    Map<String, Map<String, List<String>>> svsMapping=new HashMap<>();
		svsMapping.put("SVS_E1,E1", svsE1);
		svsMapping.put("SVS_E2,E2", svsE2);
		svsMapping.put("SVS_E3,E3", svsE3);
		svsMapping.put("SVS_E4,E4_4.1", svsE4);
		svsMapping.put("SVS_E5,E5_5.1", svsE5);
		svsMapping.put("SVS_E6,E6_6.1", svsE6);
		svsMapping.put("SVS_E7,E7", svsE7);
		svsMapping.put("SVS_E8,E8", svsE8);
		svsMapping.put("SVS_E9,E9", svsE9);
		
		Map<String, Map<String, List<String>>> dvsMapping=new HashMap<>();
		dvsMapping.put("DVS_E2,E0_02", dvsE2);
		dvsMapping.put("DVS_E3,E3_3.1", dvsE3);
		dvsMapping.put("DVS_E4,E4", dvsE4);
		dvsMapping.put("DVS_E5,E5_5.1", dvsE5);
		dvsMapping.put("DVS_E6,E6_6.1", dvsE6);
		dvsMapping.put("DVS_E7,E7", dvsE7);
		dvsMapping.put("DVS_E8,E8", dvsE8);
		dvsMapping.put("DVS_E9,E9", dvsE9);
		
		Map<String, Map<String, List<String>>> hfMapping=new HashMap<>();
		hfMapping.put("HF_E2,group_E2", hfE2);
		hfMapping.put("HF_E3,E3", hfE3);
		hfMapping.put("HF_E4,E4_4.1", hfE4);
		hfMapping.put("HF_E5,E5_5.1", hfE5);
		hfMapping.put("HF_E6,E6_6.1", hfE6);
		hfMapping.put("HF_E7,E7", hfE7);
		hfMapping.put("HF_E8,E8", hfE8);
		hfMapping.put("HF_E9,E9", hfE9);
	   
		
		List<Map<String, Map<String, List<String>>>> evmMappingList=new ArrayList<>();
		evmMappingList.add(svsMapping);
		evmMappingList.add(dvsMapping);
		evmMappingList.add(hfMapping);
		
		for(Map<String, Map<String, List<String>>> dataMap:evmMappingList){
			for(String rKey:dataMap.keySet()){
				
				 Map<String, List<String>> map=dataMap.get(rKey);
				
				EvmRequirement requirement=new EvmRequirement();
				List<EvmQuestion> evmQuestions=new ArrayList<>();
				
				requirement.setIndicatorName(rKey.split(",")[0]);
				requirement.setXpath(rKey.split(",")[1].equals("null") ? null : rKey.split(",")[1]);
				for(String key:map.keySet()){
					String info[]=key.split("#");
					EvmQuestion question=new EvmQuestion();
					
					List<EvmSubQuestion> subQuestions=new ArrayList<>();
					for(String xpath:map.get(key)){
						EvmSubQuestion subQuestion=new EvmSubQuestion();
						subQuestion.setQuestionName(xpath);
						subQuestion.setXpath(xpath);
						subQuestions.add(subQuestion);
					}
					question.setEvmSubQuestions(subQuestions);
					question.setQuestionName(info[0]);
					question.setQuestionType(info[1]);
					question.setWeightage(Double.parseDouble(info[2]));
					 if(info[3].equals("wt")){
			                question.setWeightagetype(null);
			                question.setWeightageRelevant(null);
			            }else{
			                question.setWeightagetype(info[3]);
			                question.setWeightageRelevant(info[4]);
			            }
					 if(!info[0].equals("null"))
						 question.setXpath(info[0]);
					 else
						 question.setXpath(null);
					question.setClssification(info[5]);
					evmQuestions.add(question);
				}
				requirement.setEvmQuestions(evmQuestions);
				evmRequirementRepository.save(requirement);
			}
			}
		
		System.out.println("Data inserted successfully.");
		
		}
	    
	    	
}		