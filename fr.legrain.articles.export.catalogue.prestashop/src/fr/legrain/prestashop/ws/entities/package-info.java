//@XmlJavaTypeAdapter(value=StringAdapter.class, type=String.class)
@XmlJavaTypeAdapter( value=ProductFeaturesAdapter.class, type=ProductFeatures.class )
package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fr.legrain.prestashop.ws.ProductFeaturesAdapter;

