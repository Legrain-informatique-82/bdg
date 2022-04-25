package fr.legrain.prestashop.ws.entities;

/**
 * The Order histories
 * @author nicolas
 *
 */
public class OrderHistories {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_history>
<id_order_state></id_order_state>
<id_order></id_order>
<id_employee></id_employee>
</order_history>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_history>
<id_order_state xlink:href="http://promethee.biz/prestaEb/api/order_states/" required="true" format="isUnsignedId"></id_order_state>
<id_order xlink:href="http://promethee.biz/prestaEb/api/orders/" required="true" format="isUnsignedId"></id_order>
<id_employee format="isUnsignedId"></id_employee>
</order_history>
</prestashop>

 */
	
	private int idOrderState;
	private int idOrder;
	private int idEmployee;
}
