package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaBlock;
import org.sdrc.evm.model.Block;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaBlockTranslator {

	public static Block toModel(SamikshyaBlock sBlock) {

		Block block = null;
		if (sBlock != null) {
			block = new Block();
			block.setBlockCode(sBlock.getBlockCode());
			block.setBlockId(sBlock.getBlockId());
			block.setBlockName(sBlock.getBlockName());
			block.setLastUpdatedBy(sBlock.getLastUpdatedBy());
			block.setLastUpdatedDate(sBlock.getLastUpdatedDate());
			block.setSamikshyaClusters(SamikshyaClusterTranslator
					.toModel(sBlock.getSamikshyaClusters()));
			block.setSamikshyaDistrict(new ValueObject(Integer.toString(sBlock
					.getSamikshyaDistrict().getDistrictId()), sBlock
					.getSamikshyaDistrict().getDistrictCode()));

		}

		return block;
	}

	public static List<Block> toModel(List<SamikshyaBlock> sBlocks) {
		List<Block> blocks = null;

		if (sBlocks != null && !sBlocks.isEmpty()) {
			blocks = new ArrayList<Block>();
			for (SamikshyaBlock sBlock : sBlocks) {
				blocks.add(toModel(sBlock));
			}
		}

		return blocks;
	}
}
