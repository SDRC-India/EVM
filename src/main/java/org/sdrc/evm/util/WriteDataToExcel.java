package org.sdrc.evm.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.sdrc.evm.model.BlockDataValue;

public interface WriteDataToExcel {

	InputStream writeToDCF(List<Map<String, List<BlockDataValue>>> mapList,String filePath,String district_name,String month,int year);

}
