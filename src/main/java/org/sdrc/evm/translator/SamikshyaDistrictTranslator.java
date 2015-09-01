package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaDistrict;
import org.sdrc.evm.model.District;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaDistrictTranslator {

	public static District toModel(SamikshyaDistrict sDistrict) {

		District district = null;
		if (sDistrict != null) {
			district = new District();
			district.setDistrictCode(sDistrict.getDistrictCode());
			district.setDistrictId(sDistrict.getDistrictId());
			district.setDistrictName(sDistrict.getDistrictName());
			district.setLastUpdatedBy(sDistrict.getLastUpdatedBy());
			district.setLastUpdatedDate(sDistrict.getLastUpdatedDate());
			district.setSamikshyaBlocks(SamikshyaBlockTranslator
					.toModel(sDistrict.getSamikshyaBlocks()));
			district.setSamikshyaState(new ValueObject(Integer
					.toString(sDistrict.getDistrictId()), sDistrict
					.getDistrictCode()));

		}
		return district;
	}

	public static List<District> toModel(List<SamikshyaDistrict> sDistricts) {
		List<District> districts = null;

		if (sDistricts != null && !sDistricts.isEmpty()) {
			districts = new ArrayList<District>();
			for (SamikshyaDistrict sDistrict : sDistricts) {
				districts.add(toModel(sDistrict));
			}
		}

		return districts;
	}

}
