package com.digital3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital3.schema.Inventory;
import com.digital3.sql.mapper.InventoryMapper;
import com.digital3.sql.vo.InventoryVO;



@Component
public class InventoryService {
	
	@Autowired
	InventoryMapper inventoryMapper;
	
	public Inventory inventorySearch(String productName) throws Exception {

		try {
			InventoryVO inVO = inventoryMapper.getInventory(productName);
			Inventory inventory = new Inventory();
			
			if (inVO != null) {
				inventory.setProductId(inVO.getProductId());
				inventory.setQuantity(inVO.getQuantity());
			}
			
			return inventory;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Inventory inventorySearchById(String productId) throws Exception {

		try {
			InventoryVO inVO = inventoryMapper.getInventoryById(Long.parseLong(productId));
			Inventory inventory = new Inventory();
			
			if (inVO != null) {
				inventory.setProductId(inVO.getProductId());
				inventory.setQuantity(inVO.getQuantity());
			}
			
			return inventory;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public boolean updateQuantity(Inventory inventory) throws Exception {
		try{
			if (inventorySearchById("" + inventory.getProductId()).getProductId() == 0) { //상품이 등록될때 인벤토리도 생성됨. 인벤토리가 없다는 것은 상품도 없는 것으로 간주
				throw new Exception("상품의 인벤토리가 등록되지 않은 상태입니다.");
			}
			
			InventoryVO inVO = new InventoryVO(inventory.getProductId(), inventory.getQuantity());
			int updateResult = inventoryMapper.updateQuantity(inVO);
			if(updateResult == 1)
				return true;
			else
				return false;
		} catch(Exception e) {
			throw e;
		}
	}
	
	//구매시 수량 차감
	public boolean subtractQuantity(Inventory inventory, long purchaseQuantity) throws Exception {
		try{
			if (inventorySearchById("" + inventory.getProductId()).getProductId() == 0) { //상품이 등록될때 인벤토리도 생성됨. 인벤토리가 없다는 것은 상품도 없는 것으로 간주
				throw new Exception("상품의 인벤토리가 등록되지 않은 상태입니다.");
			}
			
			if(inventory.getQuantity() - purchaseQuantity < 0) {
				throw new Exception("물량이 없습니다.");
			}
			
			InventoryVO inVO = new InventoryVO(inventory.getProductId(), inventory.getQuantity() - purchaseQuantity);
			int updateResult = inventoryMapper.updateQuantity(inVO);
			if(updateResult == 1)
				return true;
			else
				return false;
		} catch(Exception e) {
			throw e;
		}
	}
	
}
