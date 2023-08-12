package com.wellsfargo.LamaBackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="item_master")
public class Item {
	
	@Id
	@GeneratedValue(generator="uuid", strategy=GenerationType.AUTO)
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="item_id", unique=true, nullable=false)
	private String id;
	
	@Column(name="item_description", nullable=false)
	private String itemDescription;
	
	@Column(name="issue_status", nullable=false)
	private char issueStatus;
	
	@Column(name="item_make", nullable=false)
	private String itemMake;
	
	@Column(name="item_category", nullable=false)
	private String itemCategory;
	
	@Column(name="item_valuation", nullable=false)
	private int itemValuation;
	
	public Item() {}

	public Item(String id, String itemDescription, char issueStatus, String itemMake, String itemCategory,
			int itemValuation) {
		super();
		this.id = id;
		this.itemDescription = itemDescription;
		this.issueStatus = issueStatus;
		this.itemMake = itemMake;
		this.itemCategory = itemCategory;
		this.itemValuation = itemValuation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public char getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(char issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getItemMake() {
		return itemMake;
	}

	public void setItemMake(String itemMake) {
		this.itemMake = itemMake;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getItemValuation() {
		return itemValuation;
	}

	public void setItemValuation(int itemValuation) {
		this.itemValuation = itemValuation;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", itemDescription=" + itemDescription + ", issueStatus=" + issueStatus
				+ ", itemMake=" + itemMake + ", itemCategory=" + itemCategory + ", itemValuation=" + itemValuation
				+ "]";
	}
	
	
	
}
