package org.sdrc.evm.translator;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.evm.domain.SamikshyaCluster;
import org.sdrc.evm.model.Cluster;
import org.sdrc.evm.model.ValueObject;

public class SamikshyaClusterTranslator {

	public static Cluster toModel(SamikshyaCluster sCluster) {

		Cluster cluster = null;
		if (sCluster != null) {
			cluster = new Cluster();
			cluster.setClusterCode(sCluster.getClusterCode());
			cluster.setClusterId(sCluster.getClusterId());
			cluster.setClusterName(sCluster.getClusterName());
			cluster.setLastUpdatedBy(sCluster.getLastUpdatedBy());
			cluster.setLastUpdatedDate(sCluster.getLastUpdatedDate());
			cluster.setSamikshyaBlock(new ValueObject(Integer.toString(sCluster
					.getSamikshyaBlock().getBlockId()), sCluster
					.getSamikshyaBlock().getBlockCode()));
		}

		return cluster;
	}

	public static List<Cluster> toModel(List<SamikshyaCluster> sClusters) {

		List<Cluster> clusters = null;

		if (sClusters != null && !sClusters.isEmpty()) {
			clusters = new ArrayList<Cluster>();
			for (SamikshyaCluster sCluster : sClusters) {
				clusters.add(toModel(sCluster));
			}
		}
		return clusters;
	}

}
