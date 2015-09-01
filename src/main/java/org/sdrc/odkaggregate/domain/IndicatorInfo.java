package org.sdrc.odkaggregate.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="indicator_info")
public class IndicatorInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="indicator_info_id")
	private int id;
	
	@Column(name="ius_nid")
	private int iusNid;
	
	@Column(name="unit_type")
	private String unitType;
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="indicatorInfos")
	private List<EvmRequirement> requirements;
}
