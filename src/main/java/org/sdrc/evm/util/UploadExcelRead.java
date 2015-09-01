package org.sdrc.evm.util;

import org.sdrc.evm.model.UTDataAndTimePeriod;

public interface UploadExcelRead {

	public UTDataAndTimePeriod read(String filePath);
}
