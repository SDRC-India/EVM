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
public class AggregationServiceImpl implements AggregationService {

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

	@Autowired
	private AggregateService aggregateService;

	@Override
	@Transactional
	public void doAggregateByForm() {

		// get all forms from Database.
		// iterate all forms
		// for each form get xml definition,
		// get submissions of the form,
		// parse submission to get data using database xpath configuration.
		List<XForm> forms = xFormRepository.getAll();
		for (XForm form : forms) {
			// System.out.println(form.getForm_id());

			// if(form.getForm_id().equals("EVM_DVS_04082014_V1") ||
			// form.getForm_id().equals("EVM_HealthFacility_06082014_V1")){
			// if(form.getForm_id().equals("EVM_SVS_04082014_V1")){

			if (form.getForm_id().equals("EVM_DVS_09092014_V4")
					|| form.getForm_id().equals(
							"EVM_HealthFacility_09092014_V4")
					|| form.getForm_id().equals("EVM_SVS_RVS_09092014_V4")
					|| form.getForm_id().equals("EVM_DVS_Round2_14_04_2015_V2")
					|| form.getForm_id().equals(
							"EVM_HealthFacility_Gaya_Round2_14_04_2015_V2")) {
				aggregationServiceUtility.ProcessXform(form);
			}
		}
	}

	@Override
	@Transactional
	public void insertAggregationDetails() {

		HashMap<String, List<String>> svsE1 = new HashMap<String, List<String>>() {
			{
				put("E1_1.2#Boolean#5#wt#wt#M", Arrays.asList("E1_02a"));

				put("E1_1.3#Numeric#5#wt#wt#M",
						Arrays.asList("E1_03a_A", "E1_03a_B", "E1_03a_C"));

				put("E1_1.4#Numeric#1#wt#wt#M",
						Arrays.asList("E1_04a_A", "E1_04a_B"));

				put("E1_1.5#Numeric#1#eqna#E1/E1_1.5/E1_05a_B#M",
						Arrays.asList("E1_05a_A", "E1_05a_B"));

				put("E1_1.6#Numeric#1#noty#E1/E1_1.1/E1_01a_A, E1/E1_1.1/E1_01a_B, E1/E1_1.1/E1_01a_C, E1/E1_1.1/E1_01a_D#M",
						Arrays.asList("E1_06a_A", "E1_06a_B"));

				put("E1_1.7#Numeric#1#eqy#E1/E1_1.1/E1_01a_E#M",
						Arrays.asList("E1_07a_A", "E1_07a_B"));

				put("E1_1.8#Numeric#1#eqy#E1/E1_1.1/E1_01a_D#M",
						Arrays.asList("E1_08a_A", "E1_08a_B"));

				put("E1_1.9#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M",
						Arrays.asList("E1_09a_A", "E1_09a_B", "E1_09a_C",
								"E1_09a_D"));

				put("E1_1.10#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#T",
						Arrays.asList("E1_10a"));

				put("E1_1.11#Boolean#5#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M",
						Arrays.asList("E1_11a"));

				put("E1_1.13#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#M",
						Arrays.asList("E1_13a_A", "E1_13a_B", "E1_13a_C",
								"E1_13a_D", "E1_13a_E"));

				put("E1_1.14#Boolean#1#noty#E1/E1_1.1/E1_01a_A,E1/E1_1.1/E1_01a_B,E1/E1_1.1/E1_01a_C,E1/E1_1.1/E1_01a_D#V",
						Arrays.asList("E1_14a"));

				put("E1_1.17#Boolean#1#eqy#E1/E1_1.16/E1_16a_A,E1/E1_1.16/E1_16a_B,E1/E1_1.16/E1_16a_C#M",
						Arrays.asList("E1_17a_A", "E1_17a_B", "E1_17a_C",
								"E1_17a_D"));

				put("E1_1.18#Boolean#5#eqn#E1/E1_1.15/E1_15a#M",
						Arrays.asList("E1_18a_A", "E1_18a_B", "E1_18a_C"));

			}
		};
		HashMap<String, List<String>> svsE2 = new HashMap<String, List<String>>() {
			{
				put("E2_2.2.1c#Boolean#5#wt#wt#M",
						Arrays.asList("E2_01a_A", "E2_01a_B"));

				put("E2_2.2.1d#Numeric#5#noty#E0_01/group_q1/E0_01a_A#M",
						Arrays.asList("E2_02a_A", "E2_02a_B"));

				put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));

				put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));

				put("E2_2.2.1b#Boolean#1#wt#wt#T", Arrays.asList("E2_05a"));

				put("E2_2.2.3#Boolean#1#wt#wt#M", Arrays.asList("E2_07a"));

				put("E2.08A#Boolean#1#eqy#E0_01/group_q1/E0_01a_A#M",
						Arrays.asList("E2_08a"));

				put("E2_2.09A#Boolean#1#eqy#E0_01/group_q3/E0_03a_B#M",
						Arrays.asList("E2_09a"));

				put("E2_2.10A#Boolean#1#wt#wt#M", Arrays.asList("E2_10a"));

				put("E2_2.2.4#Boolean#1#wt#wt#M", Arrays.asList("E2_11a"));

				put("E2_2.2.5#Boolean#5#wt#wt#M",
						Arrays.asList("E2_12a_A", "E2_12a_B"));

				put("E2_2.3.1#Boolean#1#noty#E0_01/group_q1/E0_01a_A,E0_01/group_q3/E0_03a_B#M",
						Arrays.asList("E2_13a"));

			}
		};

		HashMap<String, List<String>> svsE3 = new HashMap<String, List<String>>() {
			{

				put("E3_3.1.1#Numeric#5#wt#wt#C", Arrays.asList("E3_01a_A",
						"estimated_cal_1/E3_01a_B", "estimated_cal_3/E3_01a_C",
						"estimated_cal_6/E3_01a_D"));

				// put("E3_3.02#Numeric#5#wt#wt#C",Arrays.asList("E3_02a_A","E3_02a_B"));
				put("E3_3.02#Numeric#5#wt#wt#C", Arrays.asList("E3_02a_B",
						"estimated1_cal/E3_02a_B", "estimated3_cal/E3_02a_C",
						"estimated6_cal/E3_02a_D"));

				// put("E3_3.1.2#Numeric#5#eqymin#E2/E2_2.2.1d/E2_02a_A#C",Arrays.asList("E3_03a_A","E3_03a_B"));

				put("E3_3.1.2#Numeric#5#eqymin#E0_01/group_q2/E0_02a_A#C",
						Arrays.asList("E3_03a_A", "estimated1_cal_1/E3_03a_B",
								"estimated1_cal_3/E3_03a_C",
								"estimated1_cal_6/E3_03a_D"));

				put("E3_3.05#Numeric#5#noty#E3/E3_3.2.1/E3_04a#C",
						Arrays.asList("E3_05a_A", "E3_05a_B"));

				put("E3_3.06#Numeric#5#noty#E3/E3_3.2.1/E3_04a,E3/E3_3.06/E3_06a_A#C",
						Arrays.asList("E3_06a_B", "E3_06a_C"));

				put("E3_3.2.2#Numeric#5#multi#E0_01/group_q2/E0_02a_A,E3/E3_3.2.1/E3_04a#C",
						Arrays.asList("E3_07a_A", "E3_07a_B"));

				put("E3_3.3.1#Numeric#5#eqn#E0_01/group_q4/E0_04a_C#C",
						Arrays.asList("E3_08a_A", "E3_08a_B"));

				put("E3_3.4.1#Boolean#5#noty#E0_01/group_q3/E0_03a_A#C",
						Arrays.asList("E3_09a_A", "E3_09a_B"));

				put("E3_3.4.2#Boolean#5#multi#E0_01/group_q3/E0_03a_A,E3/E3_3.4.2/E3_10a_A#C",
						Arrays.asList("E3_10a_A", "E3_10a_B"));

				put("E3_3.5.1#Boolean#5#wt#wt#M",
						Arrays.asList("E3.11a_A", "E3.11a_B", "E3.11a_C"));

			}
		};

		HashMap<String, List<String>> svsE4 = new HashMap<String, List<String>>() {
			{

				put("E4_4.1.1#Boolean#5#wt#wt#B",
						Arrays.asList("E4_01a_A", "E4_01a_B"));

				put("E4_4.02#Boolean#1#multi#E1/E1_1.15/E1_15a#B",
						Arrays.asList("E4_02a_A", "E4_02a_B"));

				put("E4_4.1.2#Boolean#5#wt#wt#B", Arrays.asList("E4_03a_A",
						"E4_03a_B", "E4_03a_C", "E4_03a_D", "E4_03a_E",
						"E4_03a_F", "E4_03a_G", "E4_03a_H", "E4_03a_J",
						"E4_03a_K"));

				put("E4_4.1.3#Boolean#1#multi#E2/E2_2.2.1d/E2_02a_A,E2/E2_2.2.1d/E2_02a_B,E2/E2_2.2.1d/E2_02a_C#B",
						Arrays.asList("E4_04a_A", "E4_04a_B", "E4_04a_C",
								"E4_04a_D", "E4_04a_E", "E4_04a_F", "E4_04a_G",
								"E4_04a_H", "E4_04a_J", "E4_04a_K"));

				put("E4_4.2.1#Boolean#1#wt#wt#B",
						Arrays.asList("E4_05a_A", "E4_05a_B"));

				put("E4_4.2.2#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#B",
						Arrays.asList("E4_06a_A", "E4_06a_B", "E4_06a_C",
								"E4_06a_D", "E4_06a_E", "E4_06a_F", "E4_06a_G",
								"E4_06a_H", "E4_06a_J"));

				put("E4_4.07#Boolean#5#eqy#E0_01/group_q3/E0_03a_B#B",
						Arrays.asList("E4_07a_A", "E4_07a_B", "E4_07a_C",
								"E4_07a_D", "E4_07a_E", "E4_07a_F", "E4_07a_G"));

				put("E4_4.08#Boolean#5#wt#wt#B",
						Arrays.asList("E4_08a_A", "E4_08a_B"));

				put("E4_4.2.3#Boolean#1#wt#wt#B", Arrays.asList("E4_09a_A",
						"E4_09a_B", "E4_09a_C", "E4_09a_D"));

				put("E4_4.2.4#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#B",
						Arrays.asList("E4_10a_A", "E4_10a_B"));

				put("E4_4.3.1#Boolean#5#eqy#E0_01/group_q1/E0_01a_A#E",
						Arrays.asList("E4_11a_A", "E4_11a_B", "E4_11a_C",
								"E4_11a_D", "E4_11a_E", "E4_11a_F"));

				put("E4_4.3.2#Boolean#1#eqy#E0_01/group_q1/E0_01a_A#E",
						Arrays.asList("E4_12a_A", "E4_12a_B"));

				put("E4_4.3.3#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",
						Arrays.asList("E4_13a_A", "E4_13a_B", "E4_13a_C",
								"E4_13a_D", "E4_13a_E", "E4_13a_F", "E4_13a_G",
								"E4_13a_H", "E4_13a_I", "E4_13a_J"));

				put("E4_4.3.4#EvenByOdd#1#wt#wt#E",
						Arrays.asList("E4_14a_A", "E4_14a_B"));

				put("E4_4.3.5#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A#E",
						Arrays.asList("E4_15a_B"));

				put("E4_4.16#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A,E4_4.1/E4_4.3.5/E4_15a_B#E",
						Arrays.asList("E4_16a_A", "E4_16a_B", "E4_16a_C",
								"E4_16a_D", "E4_16a_E"));

				put("E4_4.18#EvenByOdd#1#eqy#E4_4.1/E4_4.3.6/E4_17a#E",
						Arrays.asList("E4_18a_A", "E4_18a_B", "E4_18a_C",
								"E4_18a_D", "E4_18a_E", "E4_18a_F"));

				put("E4_4.3.7#EvenByOdd#1#eqy#E0_01/group_q1/E0_01a_A#E",
						Arrays.asList("E4_19a_A", "E4_19a_B", "E4_19a_C",
								"E4_19a_D"));

				put("E4_4.3.9#EvenByOdd#1#eqy#E0_01/group_q1/E0_01a_B#E",
						Arrays.asList("E4_20a_A", "E4_20a_B"));

				put("E4_4.3.8#Boolean#1#wt#wt#E", Arrays.asList("E4_21a"));

				put("E4_4.4.1#Boolean#5#eqymin#E0_01/group_q4/E0_04a_C#V",
						Arrays.asList("E4_22a_A", "E4_22a_B", "E4_22a_C"));

				put("E4_4.23#Boolean#5#eqy#E0_01/group_q3/E0_03a_B#V",
						Arrays.asList("E4_23a_A", "E4_23a_B", "E4_23a_C",
								"E4_23a_D", "E4_23a_E", "E4_23a_F", "E4_23a_G",
								"E4_23a_H"));

				put("E4_4.4.2#E4BooleanException#1#wt#wt#E",
						Arrays.asList("E4_24a_A", "E4_24a_B"));
			}
		};

		HashMap<String, List<String>> svsE5 = new HashMap<String, List<String>>() {
			{

				put("E5_5.1.1#Boolean#5#wt#wt#R",
						Arrays.asList("E5_01a_A", "E5_01a_B", "E5_01a_C"));

				put("E5_5.1.2#Boolean#5#wt#wt#R", Arrays.asList("E5_02a_A",
						"E5_02a_B", "E5_02a_C", "E5_02a_D", "E5_02a_E",
						"E5_02a_F", "E5_02a_G"));

				put("E5_5.1.3#Boolean#1#eqy#E0_01/group_q4/E0_04a_A#R",
						Arrays.asList("E5_03a_A", "E5_03a_B", "E5_03a_C",
								"E5_03a_D"));

				put("E5_5.04#EvenByOdd#5#eqy#E0_01/group_q1/E0_01a_A#R",
						Arrays.asList("E5_04a_A", "E5_04a_B"));

				put("E5_5.05#EvenByOdd#5#eqy#E0_01/group_q1/E0_01a_B#R",
						Arrays.asList("E5_05a_A", "E5_05a_B"));

				put("E5_5.2.1#EvenByOdd#5#multi#E0_01/group_q1/E0_01a_C,E5_5.1/E5_5.2.1/E5_06a_A#R",
						Arrays.asList("E5_06a_A", "E5_06a_B"));

				put("E5_5.2.2#E5Exception#1#eqy#E0_01/group_q4/E0_04a_A#R",
						Arrays.asList("E5_07a_A", "E5_07a_B"));
			}
		};

		HashMap<String, List<String>> svsE6 = new HashMap<String, List<String>>() {
			{

				put("E6_6.02#Boolean#1#eqy#E6_6.1/E6_6.1.1/E6_01a#E",
						Arrays.asList("E6_02a_A", "E6_02a_B", "E6_02a_C",
								"E6_02a_D", "E6_02a_E", "E6_02a_F", "E6_02a_G",
								"E6_02a_H", "E6_02a_J"));

				put("E6_6.1.2#Boolean#5#wt#wt#M", Arrays.asList("E6_03a"));

				put("E6_6.04#Boolean#5#wt#wt#M", Arrays.asList("E6_04a_A",
						"E6_04a_B", "E6_04a_C", "E6_04a_D", "E6_04a_E",
						"E6_04a_F", "E6_04a_G", "E6_04a_H"));

				put("E6_6.1.3#Boolean#5#wt#wt#M", Arrays.asList("E6_05a_A",
						"E6_05a_B", "E6_05a_C", "E6_05a_D", "E6_05a_E",
						"E6_05a_F", "E6_05a_G"));

				put("E6_6.1.4#Boolean#1#wt#wt#M", Arrays.asList("E6_06a"));

				put("E6_6.07#Boolean#1#wt#wt#M", Arrays.asList("E6_07a"));

				put("E6_6.1.5#Boolean#1#wt#wt#M", Arrays.asList("E6_08a"));

				put("E6_6.1.6#Boolean#1#wt#wt#M", Arrays.asList("E6_09a"));

				put("E6_6.10#Boolean#1#wt#wt#M", Arrays.asList("E6_10a"));

				put("E6_6.1.7#Boolean#5#wt#wt#M", Arrays.asList("E6_11a"));

				put("E6_6.12#Boolean#5#wt#wt#M", Arrays.asList("E6_12a"));

				put("E6_6.1.8#Boolean#1#wt#wt#M", Arrays.asList("E6_13a"));

				put("E6_6.14#Boolean#1#wt#wt#M", Arrays.asList("E6_14a"));

				put("E6_6.1.9#E6BooleanException#5#wt#wt#M", Arrays.asList(
						"E6_15a_A", "E6_15a_B", "E6_15a_C", "E6_15a_D"));

				put("E6_6.16#Numeric#5#eqy#E6_6.1/E6_6.1.9/E6_15a_A#M",
						Arrays.asList("E6_16a_B", "E6_16a_C"));

				put("E6_6.17#Boolean#1#wt#wt#M", Arrays.asList("E6_17a"));

				put("E6_6.18#Boolean#1#wt#wt#M", Arrays.asList("E6_18a"));

				put("E6_6.19#Boolean#5#wt#wt#M", Arrays.asList("E6_19a"));

				put("E6_6.2.1#Boolean#5#wt#wt#M", Arrays.asList("E6_20a_A",
						"E6_20a_B", "E6_20a_C", "E6_20a_D", "E6_20a_E"));

				put("E6_6.2.2#Boolean#5#wt#wt#M",
						Arrays.asList("E6_21a_A", "E6_21a_B"));

				put("E6_6.3.1#Numeric#5#wt#wt#M",
						Arrays.asList("E6_22a_A", "E6_22a_B"));

				put("E6_6_23#Numeric#5#wt#wt#M", Arrays.asList("E6_23a_A",
						"E6_23a_B", "E6_23a_C", "E6_23a_D", "E6_23a_E"));

				put("E6_6.3.2#Numeric#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_24a_B", "E6_24a_C", "E6_24a_D"));

				put("E6_6.4.1#Boolean#1#wt#wt#M", Arrays.asList("E6_25a_A",
						"E6_25a_B", "E6_25a_C", "E6_25a_D", "E6_25a_E"));

				put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_26a_A", "E6_26a_B", "E6_26a_C",
								"E6_26a_D"));

				put("E6_6.27#Boolean#1#wt#wt#M", Arrays.asList("E6_27a"));

			}
		};

		HashMap<String, List<String>> svsE7 = new HashMap<String, List<String>>() {
			{

				put("E7_7.01#Boolean#5#wt#wt#M", Arrays.asList("E7_01a"));

				put("E7_7.02#Numeric#1#wt#wt#M",
						Arrays.asList("E7_02a_A", "E7_02a_B"));

				put("E7_7.03#Numeric#1#wt#wt#M",
						Arrays.asList("E7_03a_A", "E7_03a_B"));

				put("E7_7.04#Boolean#1#wt#wt#M", Arrays.asList("E7_04a"));

				put("E7_7.2#Numeric#1#noty#E7/E7_7.2/E7_05a_A#M",
						Arrays.asList("E7_05a_B"));

				put("E7_7.3#Boolean#5#noty#E0_01/group_q3/E0_03a_A#T",
						Arrays.asList("E7_06a_A", "E7_06a_B"));

				put("E7_7.07#Boolean#5#noty#E0_01/group_q3/E0_03a_B#T",
						Arrays.asList("E7_07a_A", "E7_07a_B", "E7_07a_C"));

				put("E7_7.08#Boolean#5#eqna#E7/E7_7.08/E7_08a#T",
						Arrays.asList("E7_08a"));

				put("E7_7.10#Numeric#5#noty#E7/E7_7.4/E7_09a#M",
						Arrays.asList("E7_10a"));

				put("E7_7.11#Numeric#1#wt#wt#M", Arrays.asList("E7_11a_A",
						"E7_11a_B", "E7_11a_C", "E7_11a_D"));

				put("E7_7.5#Boolean#5#wt#wt#M",
						Arrays.asList("E7_12a_A", "E7_12a_B"));

				put("E7_7.13#Boolean#5#wt#wt#M",
						Arrays.asList("E7_13a_A", "E7_13a_B", "E7_13a_C"));

			}
		};

		HashMap<String, List<String>> svsE8 = new HashMap<String, List<String>>() {
			{

				put("E.8.01#Boolean#5#wt#wt#T",
						Arrays.asList("E8_01a_A", "E8_01a_B", "E8_01a_C"));

				put("E8_8.02#Boolean#1#wt#wt#M", Arrays.asList("E8_05a"));

				put("E8_8.03#Boolean#5#wt#wt#T", Arrays.asList("E8_06a"));

				put("E8_8.04#Numeric#1#wt#wt#M", Arrays.asList("E8_12a"));

				put("E8_8.05#Boolean#5#wt#wt#M", Arrays.asList("E8_13a_A",
						"E8_13a_B", "E8_13a_C", "E8_13a_D"));

				put("E8_8.06#Boolean#5#wt#wt#M", Arrays.asList("E8_14a"));

			}
		};

		HashMap<String, List<String>> svsE9 = new HashMap<String, List<String>>() {
			{

				put("E9_9.05#Numeric#5#wt#wt#M", Arrays.asList("E9_02a"));

				put("E9_9.06#Boolean#1#wt#wt#M", Arrays.asList("E9_03a"));

				put("E9_9.02#Boolean#1#wt#wt#M", Arrays.asList("E9_04a"));

				put("E9_9#Boolean#1#wt#wt#T", Arrays.asList("E9_05a"));

				put("E9_9#Boolean#1#wt#wt#T", Arrays.asList("E9_06a"));

				put("E9_9.03#Boolean#5#wt#wt#M", Arrays.asList("E9_07a_A",
						"E9_07a_A_1/E9_07a_B", "E9_07a_A_1/E9_07a_C",
						"E9_07a_A_1/E9_07a_D"));

				put("E9_9.04#Boolean#5#wt#wt#M", Arrays.asList("E9_08a"));

				put("E9_9.07#Numeric#1#wt#wt#M", Arrays.asList("E9_09a"));

				put("E9_9.08#Numeric#5#wt#wt#M", Arrays.asList("E9_10a_B"));

				put("E9_9.09#Numeric#1#eqna#E9/E9_9.09/E9_11a_A#M",
						Arrays.asList("E9_11a_A", "E9_11a_B"));

				put("E9_9.11#Boolean#1#wt#wt#M", Arrays.asList("E9_13a_A",
						"E9_13a_D", "E9_13a_C", "E9_13a_D", "E9_13a_E",
						"E9_13a_F"));

				put("E9_9.13#Boolean#5#noty#E9/E9_9.12/E9_14a_A#M",
						Arrays.asList("E9_15a_A", "E9_15a_B", "E9_15a_C",
								"E9_15a_D"));

				put("E9_9.14#Boolean#5#noty#E9/E9_9.12/E9_14a_B#M",
						Arrays.asList("E9_16a_A", "E9_16a_B", "E9_16a_C",
								"E9_16a_D"));

				put("E9_9.15#Boolean#5#noty#E9/E9_9.12/E9_14a_C#M",
						Arrays.asList("E9_17a_A", "E9_17a_B", "E9_17a_C",
								"E9_17a_D"));

				put("E9_9.16#Boolean#5#noty#E9/E9_9.12/E9_14a_D#M",
						Arrays.asList("E9_18a_A", "E9_18a_B", "E9_18a_C",
								"E9_18a_D"));

			}
		};

		HashMap<String, List<String>> dvsE2 = new HashMap<String, List<String>>() {
			{
				put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));

				put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));

				put("E2_2.2.1b#Boolean#1#wt#wt#T", Arrays.asList("E2_05a"));

				put("E2_2.2.3#Boolean#1#wt#wt#M", Arrays.asList("E2_07a"));

				put("E2_2.2.4#Boolean#1#wt#wt#M", Arrays.asList("E2_11a"));

				put("E2_2.2.5#Boolean#5#wt#wt#M",
						Arrays.asList("E2_12a_A", "E2_12a_B"));

			}
		};

		HashMap<String, List<String>> dvsE3 = new HashMap<String, List<String>>() {
			{

				put("E3_3.1.1#Numeric#5#wt#wt#C",
						Arrays.asList("E3_01a_A", "E3_01a_B"));

				put("E3_3.4.1#Boolean#5#eqy#E0_01/group_q3/E0_03a_A#C",
						Arrays.asList("E3_09a_A", "E3_09a_B"));

				put("E3_3.4.2#Boolean#5#multi#E0_01/group_q3/E0_03a_A,E3/E3_3.4.2/E3_10a_A#C",
						Arrays.asList("E3_10a_A", "E3_10a_B"));

				put("E3_3.5.1#Boolean#5#wt#wt#M",
						Arrays.asList("E3.11a_A", "E3.11a_B", "E3.11a_C"));

			}
		};

		HashMap<String, List<String>> dvsE4 = new HashMap<String, List<String>>() {
			{

				put("E4_4.1.2#Boolean#5#wt#wt#B", Arrays.asList("E4_03a_A",
						"E4_03a_B", "E4_03a_C", "E4_03a_D", "E4_03a_E",
						"E4_03a_F", "E4_03a_G", "E4_03a_H", "E4_03a_J",
						"E4_03a_K"));

				put("E4_4.3.3#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",
						Arrays.asList("E4_13a_A", "E4_13a_B", "E4_13a_C",
								"E4_13a_D", "E4_13a_E", "E4_13a_F", "E4_13a_G",
								"E4_13a_H", "E4_13a_I", "E4_13a_J"));

				put("E4_4.3.4#EvenByOdd#1#wt#wt#E",
						Arrays.asList("E4_14a_A", "E4_14a_B"));

				put("E4_4.3.5#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A#E",
						Arrays.asList("E4_15a_B"));

				put("E4_4.16#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A,E4_4.1/E4_4.3.5/E4_15a_B#E",
						Arrays.asList("E4_16a_A", "E4_16a_B", "E4_16a_C",
								"E4_16a_D", "E4_16a_E"));

				put("E4_4.18#EvenByOdd#1#eqy#E4_4.1/E4_4.3.6/E4_17a#E",
						Arrays.asList("E4_18a_A", "E4_18a_B", "E4_18a_C",
								"E4_18a_D", "E4_18a_E", "E4_18a_F"));

				put("E4_4.3.8#Boolean#1#wt#wt#E", Arrays.asList("E4_21a"));

				put("E4_4.4.1#Boolean#5#eqymin#E0_01/group_q4/E0_04a_C#V",
						Arrays.asList("E4_22a_A", "E4_22a_B", "E4_22a_C"));

				put("E4_4.4.2#E4BooleanException#1#wt#wt#E",
						Arrays.asList("E4_24a_A", "E4_24a_B"));

			}
		};

		HashMap<String, List<String>> dvsE5 = new HashMap<String, List<String>>() {
			{

				put("E5_5.1.1#Boolean#5#wt#wt#R",
						Arrays.asList("E5_01a_A", "E5_01a_B", "E5_01a_C"));

				put("E5_5.1.2#Boolean#5#wt#wt#R", Arrays.asList("E5_02a_A",
						"E5_02a_B", "E5_02a_C", "E5_02a_D", "E5_02a_E",
						"E5_02a_F", "E5_02a_G"));

				put("E5_5.1.3#Boolean#1#eqy#E0_01/group_q4/E0_04a_A#R",
						Arrays.asList("E5_03a_A", "E5_03a_B", "E5_03a_C",
								"E5_03a_D"));

				put("E5_5.05#EvenByOdd#5#eqy#E0_01/group_q1/E0_01a_B#R",
						Arrays.asList("E5_05a_A", "E5_05a_B"));

				put("E5_5.2.1#EvenByOdd#5#multi#E0_01/group_q1/E0_01a_C,E5_5.1/E5_5.2.1/E5_06a_A#R",
						Arrays.asList("E5_06a_A", "E5_06a_B"));

				put("E5_5.2.2#E5Exception#1#eqy#E0_01/group_q4/E0_04a_A#R",
						Arrays.asList("E5_07a_A", "E5_07a_B"));
			}
		};

		HashMap<String, List<String>> dvsE6 = new HashMap<String, List<String>>() {
			{

				put("E6_6.02#Boolean#1#eqy#E6_6.1/E6_6.1.1/E6_01a#E",
						Arrays.asList("E6_02a_A", "E6_02a_B", "E6_02a_C",
								"E6_02a_D", "E6_02a_E", "E6_02a_F", "E6_02a_G",
								"E6_02a_H", "E6_02a_J"));

				put("E6_6.1.2#Boolean#5#wt#wt#M", Arrays.asList("E6_03a"));

				put("E6_6.04#Boolean#5#wt#wt#M", Arrays.asList("E6_04a_A",
						"E6_04a_B", "E6_04a_C", "E6_04a_D", "E6_04a_E",
						"E6_04a_F", "E6_04a_G", "E6_04a_H"));

				put("E6_6.1.3#Boolean#5#wt#wt#M", Arrays.asList("E6_05a_A",
						"E6_05a_B", "E6_05a_C", "E6_05a_D", "E6_05a_E",
						"E6_05a_F", "E6_05a_G"));

				put("E6_6.07#Boolean#1#wt#wt#M", Arrays.asList("E6_07a"));

				put("E6_6.1.5#Boolean#1#wt#wt#M", Arrays.asList("E6_08a"));

				put("E6_6.1.6#Boolean#1#wt#wt#M", Arrays.asList("E6_09a"));

				put("E6_6.10#Boolean#1#wt#wt#M", Arrays.asList("E6_10a"));

				put("E6_6.1.8#Boolean#1#wt#wt#M", Arrays.asList("E6_13a"));

				put("E6_6.14#Boolean#1#wt#wt#M", Arrays.asList("E6_14a"));

				put("E6_6.1.9#E6BooleanException#5#wt#wt#M", Arrays.asList(
						"E6_15a_A", "E6_15a_B", "E6_15a_C", "E6_15a_D"));

				put("E6_6.16#Numeric#5#eqy#E6_6.1/E6_6.1.9/E6_15a_A#M",
						Arrays.asList("E6_16a_B", "E6_16a_C"));

				put("E6_6.17#Boolean#1#wt#wt#M", Arrays.asList("E6_17a"));

				put("E6_6.18#Boolean#1#wt#wt#M", Arrays.asList("E6_18a"));

				put("E6_6.19#Boolean#5#wt#wt#M", Arrays.asList("E6_19a"));

				put("E6_6.2.1#Boolean#5#wt#wt#M", Arrays.asList("E6_20a_A",
						"E6_20a_B", "E6_20a_C", "E6_20a_D", "E6_20a_E"));

				put("E6_6.2.2#Boolean#5#wt#wt#M",
						Arrays.asList("E6_21a_A", "E6_21a_B"));

				put("E6_6.3.1#Numeric#5#wt#wt#M",
						Arrays.asList("E6_22a_A", "E6_22a_B"));

				put("E6_6_23#Numeric#5#wt#wt#M", Arrays.asList("E6_23a_A",
						"E6_23a_B", "E6_23a_C", "E6_23a_D", "E6_23a_E"));

				put("E6_6.3.2#Numeric#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_24a_B", "E6_24a_C", "E6_24a_D"));

				put("E6_6.4.1#Boolean#1#wt#wt#M", Arrays.asList("E6_25a_A",
						"E6_25a_B", "E6_25a_C", "E6_25a_D", "E6_25a_E"));

				put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_26a_A", "E6_26a_B", "E6_26a_C",
								"E6_26a_D"));

				put("E6_6.27#Boolean#1#wt#wt#M", Arrays.asList("E6_27a"));

			}
		};

		HashMap<String, List<String>> dvsE7 = new HashMap<String, List<String>>() {
			{

				put("E7_7.01#Boolean#5#wt#wt#M", Arrays.asList("E7_01a"));

				put("E7_7.02#Numeric#1#wt#wt#M",
						Arrays.asList("E7_02a_A", "E7_02a_B"));

				put("E7_7.03#Numeric#1#wt#wt#M",
						Arrays.asList("E7_03a_A", "E7_03a_B"));

				put("E7_7.04#Boolean#1#wt#wt#M", Arrays.asList("E7_04a"));

				put("E7_7.2#Numeric#1#noty#E7/E7_7.2/E7_05a_A#M",
						Arrays.asList("E7_05a_B"));

				put("E7_7.3#Boolean#5#noty#E0_01/group_q3/E0_03a_A#T",
						Arrays.asList("E7_06a_A", "E7_06a_B"));

				put("E7_7.08#Boolean#5#eqna#E7/E7_7.08/E7_08a#T",
						Arrays.asList("E7_08a"));

				put("E7_7.10#Numeric#5#noty#E7/E7_7.4/E7_09a#M",
						Arrays.asList("E7_10a"));

				put("E7_7.11#Numeric#1#wt#wt#M", Arrays.asList("E7_11a_A",
						"E7_11a_B", "E7_11a_C", "E7_11a_D"));

				put("E7_7.5#Boolean#5#wt#wt#M",
						Arrays.asList("E7_12a_A", "E7_12a_B"));

			}
		};

		HashMap<String, List<String>> dvsE8 = new HashMap<String, List<String>>() {
			{

				put("E.8.01#Boolean#5#wt#wt#T",
						Arrays.asList("E8_01a_A", "E8_01a_B", "E8_01a_C"));

				put("E8_8.02#Boolean#1#wt#wt#M", Arrays.asList("E8_05a"));

				put("E8_8.03#Boolean#5#wt#wt#T", Arrays.asList("E8_06a"));

				put("E8_8.04#Numeric#1#wt#wt#M", Arrays.asList("E8_12a"));

				put("E8_8.05#Boolean#5#wt#wt#M", Arrays.asList("E8_13a_A",
						"E8_13a_B", "E8_13a_C", "E8_13a_D"));

				put("E8_8.06#Boolean#5#wt#wt#M", Arrays.asList("E8_14a"));

			}
		};

		HashMap<String, List<String>> dvsE9 = new HashMap<String, List<String>>() {
			{

				put("E9_9.02#Boolean#1#wt#wt#M", Arrays.asList("E9_04a"));

				put("E9_9.03#Boolean#5#wt#wt#M", Arrays.asList("E9_07a_A",
						"E9_07a_A_1/E9_07a_B", "E9_07a_A_1/E9_07a_C",
						"E9_07a_A_1/E9_07a_D"));

				put("null#Boolean#5#wt#wt#M", Arrays.asList("E9_08a"));

			}
		};

		HashMap<String, List<String>> hfE2 = new HashMap<String, List<String>>() {
			{
				put("E2_2.2.1#Boolean#5#wt#wt#T", Arrays.asList("E2_03a"));

				put("E2_2.2.1a#Boolean#5#wt#wt#T", Arrays.asList("E2_04a"));

				put("E2_2.2.3#Boolean#1#wt#wt#M", Arrays.asList("E2_07a"));

				put("E2_2.2.4#Boolean#1#wt#wt#M", Arrays.asList("E2_11a"));

				put("E2_2.2.5#Boolean#5#wt#wt#M",
						Arrays.asList("E2_12a_A", "E2_12a_B"));

			}
		};

		HashMap<String, List<String>> hfE3 = new HashMap<String, List<String>>() {
			{

				put("E3_3.1.1#Numeric#5#wt#wt#C",
						Arrays.asList("E3_01a_A", "estimated_cal_1/E3_01a_B"));

				put("E3_3.4.1#Boolean#5#noty#E0_01/group_q3/E0_03a_A#C",
						Arrays.asList("E3_09a_A", "E3_09a_B"));

				put("E3_3.4.2#Boolean#5#multi#E0_01/group_q3/E0_03a_A,E3/E3_3.4.2/E3_10a_A#C",
						Arrays.asList("E3_10a_A", "E3_10a_B"));

				put("E3_3.5.1#Boolean#5#wt#wt#M",
						Arrays.asList("E3.11a_A", "E3.11a_B", "E3.11a_C"));

			}
		};

		HashMap<String, List<String>> hfE4 = new HashMap<String, List<String>>() {
			{

				put("E4_4.1.2#Boolean#5#wt#wt#B", Arrays.asList("E4_03a_A",
						"E4_03a_B", "E4_03a_C", "E4_03a_D", "E4_03a_E",
						"E4_03a_F", "E4_03a_G", "E4_03a_H", "E4_03a_J",
						"E4_03a_K"));

				put("E4_4.3.3#Boolean#5#eqy#E0_01/group_q1/E0_01a_B#E",
						Arrays.asList("E4_13a_A", "E4_13a_B", "E4_13a_C",
								"E4_13a_D", "E4_13a_E", "E4_13a_F", "E4_13a_G",
								"E4_13a_H", "E4_13a_I", "E4_13a_J"));

				put("E4_4.3.4#EvenByOdd#1#wt#wt#E",
						Arrays.asList("E4_14a_A", "E4_14a_B"));

				put("E4_4.3.5#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A#E",
						Arrays.asList("E4_15a_B"));

				put("E4_4.16#Boolean#5#eqy#E4_4.1/E4_4.3.5/E4_15a_A,E4_4.1/E4_4.3.5/E4_15a_B#E",
						Arrays.asList("E4_16a_A", "E4_16a_B", "E4_16a_C",
								"E4_16a_D", "E4_16a_E"));

				put("E4_4.18#EvenByOdd#1#eqy#E4_4.1/E4_4.3.6/E4_17a#E",
						Arrays.asList("E4_18a_A", "E4_18a_B", "E4_18a_C",
								"E4_18a_D", "E4_18a_E", "E4_18a_F"));

				put("E4_4.3.8#Boolean#1#wt#wt#E", Arrays.asList("E4_21a"));

				put("E4_4.4.2#E4BooleanException#1#wt#wt#E",
						Arrays.asList("E4_24a_A", "E4_24a_B"));
			}
		};

		HashMap<String, List<String>> hfE5 = new HashMap<String, List<String>>() {
			{

				put("E5_5.1.1#Boolean#5#wt#wt#R",
						Arrays.asList("E5_01a_A", "E5_01a_B", "E5_01a_C"));

				put("E5_5.1.2#Boolean#5#wt#wt#R", Arrays.asList("E5_02a_A",
						"E5_02a_B", "E5_02a_C", "E5_02a_D", "E5_02a_E",
						"E5_02a_F", "E5_02a_G"));

				put("E5_5.05#EvenByOdd#5#eqy#E0_01/group_q1/E0_01a_B#R",
						Arrays.asList("E5_05a_A", "E5_05a_B"));

			}
		};

		HashMap<String, List<String>> hfE6 = new HashMap<String, List<String>>() {
			{

				put("E6_6.1.2#Boolean#5#wt#wt#M", Arrays.asList("E6_03a"));

				put("E6_6.04#Boolean#5#wt#wt#M", Arrays.asList("E6_04a_A",
						"E6_04a_B", "E6_04a_C", "E6_04a_D", "E6_04a_E",
						"E6_04a_F", "E6_04a_G", "E6_04a_H"));

				put("E6_6.1.3#Boolean#5#wt#wt#M", Arrays.asList("E6_05a_A",
						"E6_05a_B", "E6_05a_C", "E6_05a_D", "E6_05a_E",
						"E6_05a_F", "E6_05a_G"));

				put("E6_6.07#Boolean#1#wt#wt#M", Arrays.asList("E6_07a"));

				put("E6_6.1.5#Boolean#1#wt#wt#M", Arrays.asList("E6_08a"));

				put("E6_6.1.6#Boolean#1#wt#wt#M", Arrays.asList("E6_09a"));

				put("E6_6.10#Boolean#1#wt#wt#M", Arrays.asList("E6_10a"));

				put("E6_6.1.8#Boolean#1#wt#wt#M", Arrays.asList("E6_13a"));

				put("E6_6.14#Boolean#1#wt#wt#M", Arrays.asList("E6_14a"));

				put("E6_6.1.9#E6BooleanException#5#wt#wt#M", Arrays.asList(
						"E6_15a_A", "E6_15a_B", "E6_15a_C", "E6_15a_D"));

				put("E6_6.16#Numeric#5#eqy#E6_6.1/E6_6.1.9/E6_15a_A#M",
						Arrays.asList("E6_16a_B", "E6_16a_C"));

				put("E6_6.17#Boolean#1#wt#wt#M", Arrays.asList("E6_17a"));

				put("E6_6.18#Boolean#1#wt#wt#M", Arrays.asList("E6_18a"));

				put("E6_6.19#Boolean#5#wt#wt#M", Arrays.asList("E6_19a"));

				put("E6_6.2.2#Boolean#5#wt#wt#M",
						Arrays.asList("E6_21a_A", "E6_21a_B"));

				put("E6_6.3.1#Numeric#5#wt#wt#M",
						Arrays.asList("E6_22a_A", "E6_22a_B"));

				put("E6_6_23#Numeric#5#wt#wt#M", Arrays.asList("E6_23a_A",
						"E6_23a_B", "E6_23a_C", "E6_23a_D", "E6_23a_E"));

				put("E6_6.3.2#Numeric#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_24a_B", "E6_24a_C", "E6_24a_D"));

				put("E6_6.4.1#Boolean#1#wt#wt#M", Arrays.asList("E6_25a_A",
						"E6_25a_B", "E6_25a_C", "E6_25a_D", "E6_25a_E"));

				put("E6_6.4.2#Boolean#1#eqymin#E0_01/group_q2/E0_02a_A#M",
						Arrays.asList("E6_26a_A", "E6_26a_B", "E6_26a_C",
								"E6_26a_D"));

				put("E6_6.27#Boolean#1#wt#wt#M", Arrays.asList("E6_27a"));

			}
		};

		HashMap<String, List<String>> hfE7 = new HashMap<String, List<String>>() {
			{

				put("E7_7.3#Boolean#5#noty#E0_01/group_q3/E0_03a_A#T",
						Arrays.asList("E7_06a_A", "E7_06a_B"));

				put("E7_7.08#Boolean#5#eqna#E7/E7_7.08/E7_08a#T",
						Arrays.asList("E7_08a"));

				put("E7_7.5#Boolean#5#wt#wt#M",
						Arrays.asList("E7_12a_A", "E7_12a_B"));

			}
		};

		HashMap<String, List<String>> hfE8 = new HashMap<String, List<String>>() {
			{
				put("E.8.01#Boolean#5#wt#wt#T",
						Arrays.asList("E8_01a_A", "E8_01a_B", "E8_01a_C"));

				put("E.8.02#Boolean#5#wt#wt#T", Arrays.asList("E8_02a"));

				put("E.8.03#Boolean#5#wt#wt#T", Arrays.asList("E8_03a"));

				put("E.8.04#Boolean#5#wt#wt#T", Arrays.asList("E8_04a"));

				put("E.8.05#Boolean#1#wt#wt#M", Arrays.asList("E8_05a"));

				put("E.8.06#Boolean#5#wt#wt#T", Arrays.asList("E8_06a"));

				put("E.8.07#Boolean#5#wt#wt#M", Arrays.asList("E8_07a"));

				put("E.8.08#Boolean#5#wt#wt#M", Arrays.asList("E8_08a"));

				put("E.8.10#Boolean#5#eqn#E8/E.8.09/E8_09a#T",
						Arrays.asList("E8_10a"));

				put("E.8.11#Boolean#5#eqn#E8/E.8.09/E8_09a#T",
						Arrays.asList("E8_11a"));

				put("E.8.12#Numeric#1#wt#wt#M", Arrays.asList("E8_12a"));

				put("E.8.13#Boolean#5#wt#wt#M", Arrays.asList("E8_13a_A",
						"E8_13a_B", "E8_13a_C", "E8_13a_D"));

				put("E.8.14#Boolean#5#wt#wt#M", Arrays.asList("E8_14a"));

				put("E.8.15#E8_15_BooleanException#1#wt#wt#M", Arrays.asList(
						"E8_15a_A", "E8_15a_B", "E8_15a_C", "E8_15a_D",
						"E8_15a_E"));

				put("E.8.16#E8_16_BooleanException#1#wt#wt#M",
						Arrays.asList("E8_16a_A", "E8_16a_B", "E8_16a_C"));
			}
		};

		Map<String, Map<String, List<String>>> svsMapping = new HashMap<>();
		svsMapping.put("SVS_E1,E1", svsE1);
		svsMapping.put("SVS_E2,E2", svsE2);
		svsMapping.put("SVS_E3,E3", svsE3);
		svsMapping.put("SVS_E4,E4_4.1", svsE4);
		svsMapping.put("SVS_E5,E5_5.1", svsE5);
		svsMapping.put("SVS_E6,E6_6.1", svsE6);
		svsMapping.put("SVS_E7,E7", svsE7);
		svsMapping.put("SVS_E8,E8", svsE8);
		svsMapping.put("SVS_E9,E9", svsE9);

		Map<String, Map<String, List<String>>> dvsMapping = new HashMap<>();
		dvsMapping.put("DVS_E2,null", dvsE2);
		dvsMapping.put("DVS_E3,E3_3.1", dvsE3);
		dvsMapping.put("DVS_E4,E4_4.1", dvsE4);
		dvsMapping.put("DVS_E5,E5_5.1", dvsE5);
		dvsMapping.put("DVS_E6,E6_6.1", dvsE6);
		dvsMapping.put("DVS_E7,E7", dvsE7);
		dvsMapping.put("DVS_E8,E7/E8", dvsE8);
		dvsMapping.put("DVS_E9,E7/E9", dvsE9);

		Map<String, Map<String, List<String>>> hfMapping = new HashMap<>();
		hfMapping.put("HF_E2,group_E2", hfE2);
		hfMapping.put("HF_E3,E3", hfE3);
		hfMapping.put("HF_E4,E4_4.1", hfE4);
		hfMapping.put("HF_E5,E5_5.1", hfE5);
		hfMapping.put("HF_E6,E6_6.1", hfE6);
		hfMapping.put("HF_E7,E7", hfE7);
		hfMapping.put("HF_E8,E8", hfE8);

		List<Map<String, Map<String, List<String>>>> evmMappingList = new ArrayList<>();
		evmMappingList.add(svsMapping);
		evmMappingList.add(dvsMapping);
		evmMappingList.add(hfMapping);
		//
		// for(Map<String, Map<String, List<String>>> dataMap:evmMappingList){
		// for(String rKey:dataMap.keySet()){
		//
		// Map<String, List<String>> map=dataMap.get(rKey);
		//
		// EvmRequirement requirement=new EvmRequirement();
		// List<EvmQuestion> evmQuestions=new ArrayList<>();
		//
		// requirement.setIndicatorName(rKey.split(",")[0]);
		// requirement.setXpath(rKey.split(",")[1].equals("null") ? null :
		// rKey.split(",")[1]);
		// for(String key:map.keySet()){
		// String info[]=key.split("#");
		// EvmQuestion question=new EvmQuestion();
		//
		// List<EvmSubQuestion> subQuestions=new ArrayList<>();
		// for(String xpath:map.get(key)){
		// EvmSubQuestion subQuestion=new EvmSubQuestion();
		// subQuestion.setQuestionName(xpath);
		// subQuestion.setXpath(xpath);
		// subQuestions.add(subQuestion);
		// }
		// question.setEvmSubQuestions(subQuestions);
		// question.setQuestionName(info[0]);
		// question.setQuestionType(info[1]);
		// question.setWeightage(Double.parseDouble(info[2]));
		// if(info[3].equals("wt")){
		// question.setWeightagetype(null);
		// question.setWeightageRelevant(null);
		// }else{
		// question.setWeightagetype(info[3]);
		// question.setWeightageRelevant(info[4]);
		// }
		// if(!info[0].equals("null"))
		// question.setXpath(info[0]);
		// else
		// question.setXpath(null);
		// question.setClssification(info[5]);
		// evmQuestions.add(question);
		// }
		// requirement.setEvmQuestions(evmQuestions);
		// evmRequirementRepository.save(requirement);
		// }
		// }
		//
		// System.out.println("Data inserted successfully.");
		//
		aggregateService.insertAggregateDetails();
	}

}
