package Magento;

public class Mage_Api_Model_Server_V2_HandlerPortTypeProxy implements Magento.Mage_Api_Model_Server_V2_HandlerPortType {
  private String _endpoint = null;
  private Magento.Mage_Api_Model_Server_V2_HandlerPortType mage_Api_Model_Server_V2_HandlerPortType = null;
  
  public Mage_Api_Model_Server_V2_HandlerPortTypeProxy() {
    _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
  }
  
  public Mage_Api_Model_Server_V2_HandlerPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
  }
  
  private void _initMage_Api_Model_Server_V2_HandlerPortTypeProxy() {
    try {
      mage_Api_Model_Server_V2_HandlerPortType = (new Magento.MagentoServiceLocator()).getMage_Api_Model_Server_V2_HandlerPort();
      if (mage_Api_Model_Server_V2_HandlerPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mage_Api_Model_Server_V2_HandlerPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mage_Api_Model_Server_V2_HandlerPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mage_Api_Model_Server_V2_HandlerPortType != null)
      ((javax.xml.rpc.Stub)mage_Api_Model_Server_V2_HandlerPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Magento.Mage_Api_Model_Server_V2_HandlerPortType getMage_Api_Model_Server_V2_HandlerPortType() {
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType;
  }
  
  public boolean endSession(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.endSession(sessionId);
  }
  
  public java.lang.String login(java.lang.String username, java.lang.String apiKey) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.login(username, apiKey);
  }
  
  public java.lang.String startSession() throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.startSession();
  }
  
  public Magento.ApiEntity[] resources(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.resources(sessionId);
  }
  
  public Magento.ExistsFaltureEntity[] globalFaults(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.globalFaults(sessionId);
  }
  
  public Magento.ExistsFaltureEntity[] resourceFaults(java.lang.String resourceName, java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.resourceFaults(resourceName, sessionId);
  }
  
  public Magento.DirectoryCountryEntity[] directoryCountryList(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.directoryCountryList(sessionId);
  }
  
  public Magento.DirectoryRegionEntity[] directoryRegionList(java.lang.String sessionId, java.lang.String country) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.directoryRegionList(sessionId, country);
  }
  
  public Magento.CustomerCustomerEntity[] customerCustomerList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerCustomerList(sessionId, filters);
  }
  
  public int customerCustomerCreate(java.lang.String sessionId, Magento.CustomerCustomerEntityToCreate customerData) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerCustomerCreate(sessionId, customerData);
  }
  
  public Magento.CustomerCustomerEntity customerCustomerInfo(java.lang.String sessionId, int customerId, java.lang.String[] attributes) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerCustomerInfo(sessionId, customerId, attributes);
  }
  
  public boolean customerCustomerUpdate(java.lang.String sessionId, int customerId, Magento.CustomerCustomerEntityToCreate customerData) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerCustomerUpdate(sessionId, customerId, customerData);
  }
  
  public boolean customerCustomerDelete(java.lang.String sessionId, int customerId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerCustomerDelete(sessionId, customerId);
  }
  
  public Magento.CustomerGroupEntity[] customerGroupList(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerGroupList(sessionId);
  }
  
  public Magento.CustomerAddressEntityItem[] customerAddressList(java.lang.String sessionId, int customerId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerAddressList(sessionId, customerId);
  }
  
  public int customerAddressCreate(java.lang.String sessionId, int customerId, Magento.CustomerAddressEntityCreate addressData) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerAddressCreate(sessionId, customerId, addressData);
  }
  
  public Magento.CustomerAddressEntityItem customerAddressInfo(java.lang.String sessionId, int addressId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerAddressInfo(sessionId, addressId);
  }
  
  public boolean customerAddressUpdate(java.lang.String sessionId, int addressId, Magento.CustomerAddressEntityCreate addressData) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerAddressUpdate(sessionId, addressId, addressData);
  }
  
  public boolean customerAddressDelete(java.lang.String sessionId, int addressId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.customerAddressDelete(sessionId, addressId);
  }
  
  public int catalogCategoryCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryCurrentStore(sessionId, storeView);
  }
  
  public Magento.CatalogCategoryTree catalogCategoryTree(java.lang.String sessionId, java.lang.String parentId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryTree(sessionId, parentId, storeView);
  }
  
  public Magento.CatalogCategoryEntityNoChildren[] catalogCategoryLevel(java.lang.String sessionId, java.lang.String website, java.lang.String storeView, java.lang.String parentCategory) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryLevel(sessionId, website, storeView, parentCategory);
  }
  
  public Magento.CatalogCategoryInfo catalogCategoryInfo(java.lang.String sessionId, int categoryId, java.lang.String storeView, java.lang.String[] attributes) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryInfo(sessionId, categoryId, storeView, attributes);
  }
  
  public int catalogCategoryCreate(java.lang.String sessionId, int parentId, Magento.CatalogCategoryEntityCreate categoryData, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryCreate(sessionId, parentId, categoryData, storeView);
  }
  
  public boolean catalogCategoryUpdate(java.lang.String sessionId, int categoryId, Magento.CatalogCategoryEntityCreate categoryData, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryUpdate(sessionId, categoryId, categoryData, storeView);
  }
  
  public boolean catalogCategoryMove(java.lang.String sessionId, int categoryId, int parentId, java.lang.String afterId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryMove(sessionId, categoryId, parentId, afterId);
  }
  
  public boolean catalogCategoryDelete(java.lang.String sessionId, int categoryId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryDelete(sessionId, categoryId);
  }
  
  public Magento.CatalogAssignedProduct[] catalogCategoryAssignedProducts(java.lang.String sessionId, int categoryId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryAssignedProducts(sessionId, categoryId);
  }
  
  public boolean catalogCategoryAssignProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String position, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryAssignProduct(sessionId, categoryId, product, position, productIdentifierType);
  }
  
  public boolean catalogCategoryUpdateProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String position, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryUpdateProduct(sessionId, categoryId, product, position, productIdentifierType);
  }
  
  public boolean catalogCategoryRemoveProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryRemoveProduct(sessionId, categoryId, product, productIdentifierType);
  }
  
  public int catalogCategoryAttributeCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryAttributeCurrentStore(sessionId, storeView);
  }
  
  public Magento.CatalogAttributeEntity[] catalogCategoryAttributeList(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryAttributeList(sessionId);
  }
  
  public Magento.CatalogAttributeOptionEntity[] catalogCategoryAttributeOptions(java.lang.String sessionId, java.lang.String attributeId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogCategoryAttributeOptions(sessionId, attributeId, storeView);
  }
  
  public int catalogProductCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductCurrentStore(sessionId, storeView);
  }
  
  public Magento.CatalogProductEntity[] catalogProductList(java.lang.String sessionId, Magento.Filters filters, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductList(sessionId, filters, storeView);
  }
  
  public Magento.CatalogProductReturnEntity catalogProductInfo(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, Magento.CatalogProductRequestAttributes attributes, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductInfo(sessionId, product, storeView, attributes, productIdentifierType);
  }
  
  public int catalogProductCreate(java.lang.String sessionId, java.lang.String type, java.lang.String set, java.lang.String sku, Magento.CatalogProductCreateEntity productData, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductCreate(sessionId, type, set, sku, productData, storeView);
  }
  
  public boolean catalogProductUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductCreateEntity productData, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductUpdate(sessionId, product, productData, storeView, productIdentifierType);
  }
  
  public int catalogProductSetSpecialPrice(java.lang.String sessionId, java.lang.String product, java.lang.String specialPrice, java.lang.String fromDate, java.lang.String toDate, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductSetSpecialPrice(sessionId, product, specialPrice, fromDate, toDate, storeView, productIdentifierType);
  }
  
  public Magento.CatalogProductReturnEntity catalogProductGetSpecialPrice(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductGetSpecialPrice(sessionId, product, storeView, productIdentifierType);
  }
  
  public int catalogProductDelete(java.lang.String sessionId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductDelete(sessionId, product, productIdentifierType);
  }
  
  public int catalogProductAttributeCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeCurrentStore(sessionId, storeView);
  }
  
  public Magento.CatalogAttributeEntity[] catalogProductAttributeList(java.lang.String sessionId, int setId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeList(sessionId, setId);
  }
  
  public Magento.CatalogAttributeOptionEntity[] catalogProductAttributeOptions(java.lang.String sessionId, java.lang.String attributeId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeOptions(sessionId, attributeId, storeView);
  }
  
  public Magento.CatalogProductAttributeSetEntity[] catalogProductAttributeSetList(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeSetList(sessionId);
  }
  
  public Magento.CatalogProductTypeEntity[] catalogProductTypeList(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductTypeList(sessionId);
  }
  
  public Magento.CatalogProductTierPriceEntity[] catalogProductAttributeTierPriceInfo(java.lang.String sessionId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeTierPriceInfo(sessionId, product, productIdentifierType);
  }
  
  public int catalogProductAttributeTierPriceUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductTierPriceEntity[] tier_price, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeTierPriceUpdate(sessionId, product, tier_price, productIdentifierType);
  }
  
  public int catalogProductAttributeMediaCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaCurrentStore(sessionId, storeView);
  }
  
  public Magento.CatalogProductImageEntity[] catalogProductAttributeMediaList(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaList(sessionId, product, storeView, productIdentifierType);
  }
  
  public Magento.CatalogProductImageEntity catalogProductAttributeMediaInfo(java.lang.String sessionId, java.lang.String product, java.lang.String file, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaInfo(sessionId, product, file, storeView, productIdentifierType);
  }
  
  public Magento.CatalogProductAttributeMediaTypeEntity[] catalogProductAttributeMediaTypes(java.lang.String sessionId, java.lang.String setId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaTypes(sessionId, setId);
  }
  
  public java.lang.String catalogProductAttributeMediaCreate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductAttributeMediaCreateEntity data, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaCreate(sessionId, product, data, storeView, productIdentifierType);
  }
  
  public int catalogProductAttributeMediaUpdate(java.lang.String sessionId, java.lang.String product, java.lang.String file, Magento.CatalogProductAttributeMediaCreateEntity data, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaUpdate(sessionId, product, file, data, storeView, productIdentifierType);
  }
  
  public int catalogProductAttributeMediaRemove(java.lang.String sessionId, java.lang.String product, java.lang.String file, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductAttributeMediaRemove(sessionId, product, file, productIdentifierType);
  }
  
  public Magento.CatalogProductLinkEntity[] catalogProductLinkList(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkList(sessionId, type, product, productIdentifierType);
  }
  
  public java.lang.String catalogProductLinkAssign(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, Magento.CatalogProductLinkEntity data, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkAssign(sessionId, type, product, linkedProduct, data, productIdentifierType);
  }
  
  public java.lang.String catalogProductLinkUpdate(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, Magento.CatalogProductLinkEntity data, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkUpdate(sessionId, type, product, linkedProduct, data, productIdentifierType);
  }
  
  public java.lang.String catalogProductLinkRemove(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, java.lang.String productIdentifierType) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkRemove(sessionId, type, product, linkedProduct, productIdentifierType);
  }
  
  public java.lang.String[] catalogProductLinkTypes(java.lang.String sessionId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkTypes(sessionId);
  }
  
  public Magento.CatalogProductLinkAttributeEntity[] catalogProductLinkAttributes(java.lang.String sessionId, java.lang.String type) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogProductLinkAttributes(sessionId, type);
  }
  
  public Magento.SalesOrderListEntity[] salesOrderList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderList(sessionId, filters);
  }
  
  public Magento.SalesOrderEntity salesOrderInfo(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInfo(sessionId, orderIncrementId);
  }
  
  public int salesOrderAddComment(java.lang.String sessionId, java.lang.String orderIncrementId, java.lang.String status, java.lang.String comment, java.lang.String notify) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderAddComment(sessionId, orderIncrementId, status, comment, notify);
  }
  
  public int salesOrderHold(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderHold(sessionId, orderIncrementId);
  }
  
  public int salesOrderUnhold(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderUnhold(sessionId, orderIncrementId);
  }
  
  public int salesOrderCancel(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderCancel(sessionId, orderIncrementId);
  }
  
  public Magento.SalesOrderShipmentEntity[] salesOrderShipmentList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentList(sessionId, filters);
  }
  
  public Magento.SalesOrderShipmentEntity salesOrderShipmentInfo(java.lang.String sessionId, java.lang.String shipmentIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentInfo(sessionId, shipmentIncrementId);
  }
  
  public java.lang.String salesOrderShipmentCreate(java.lang.String sessionId, java.lang.String orderIncrementId, Magento.OrderItemIdQty[] itemsQty, java.lang.String comment, int email, int includeComment) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentCreate(sessionId, orderIncrementId, itemsQty, comment, email, includeComment);
  }
  
  public int salesOrderShipmentAddComment(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String comment, java.lang.String email, java.lang.String includeInEmail) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentAddComment(sessionId, shipmentIncrementId, comment, email, includeInEmail);
  }
  
  public int salesOrderShipmentAddTrack(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String carrier, java.lang.String title, java.lang.String trackNumber) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentAddTrack(sessionId, shipmentIncrementId, carrier, title, trackNumber);
  }
  
  public int salesOrderShipmentRemoveTrack(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String trackId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentRemoveTrack(sessionId, shipmentIncrementId, trackId);
  }
  
  public Magento.AssociativeEntity[] salesOrderShipmentGetCarriers(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderShipmentGetCarriers(sessionId, orderIncrementId);
  }
  
  public Magento.SalesOrderInvoiceEntity[] salesOrderInvoiceList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceList(sessionId, filters);
  }
  
  public Magento.SalesOrderInvoiceEntity salesOrderInvoiceInfo(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceInfo(sessionId, invoiceIncrementId);
  }
  
  public java.lang.String salesOrderInvoiceCreate(java.lang.String sessionId, java.lang.String invoiceIncrementId, Magento.OrderItemIdQty[] itemsQty, java.lang.String comment, java.lang.String email, java.lang.String includeComment) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceCreate(sessionId, invoiceIncrementId, itemsQty, comment, email, includeComment);
  }
  
  public java.lang.String salesOrderInvoiceAddComment(java.lang.String sessionId, java.lang.String invoiceIncrementId, java.lang.String comment, java.lang.String email, java.lang.String includeComment) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceAddComment(sessionId, invoiceIncrementId, comment, email, includeComment);
  }
  
  public java.lang.String salesOrderInvoiceCapture(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceCapture(sessionId, invoiceIncrementId);
  }
  
  public java.lang.String salesOrderInvoiceVoid(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceVoid(sessionId, invoiceIncrementId);
  }
  
  public java.lang.String salesOrderInvoiceCancel(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.salesOrderInvoiceCancel(sessionId, invoiceIncrementId);
  }
  
  public Magento.CatalogInventoryStockItemEntity[] catalogInventoryStockItemList(java.lang.String sessionId, java.lang.String[] products) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogInventoryStockItemList(sessionId, products);
  }
  
  public int catalogInventoryStockItemUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogInventoryStockItemUpdateEntity data) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.catalogInventoryStockItemUpdate(sessionId, product, data);
  }
  
  public int shoppingCartCreate(java.lang.String sessionId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartCreate(sessionId, storeId);
  }
  
  public Magento.ShoppingCartInfoEntity shoppingCartInfo(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartInfo(sessionId, quoteId, storeId);
  }
  
  public java.lang.String shoppingCartOrder(java.lang.String sessionId, int quoteId, java.lang.String storeId, java.lang.String[] licenses) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartOrder(sessionId, quoteId, storeId, licenses);
  }
  
  public Magento.ShoppingCartTotalsEntity[] shoppingCartTotals(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartTotals(sessionId, quoteId, storeId);
  }
  
  public Magento.ShoppingCartLicenseEntity[] shoppingCartLicense(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartLicense(sessionId, quoteId, storeId);
  }
  
  public boolean shoppingCartProductAdd(java.lang.String sessionId, int quoteId, Magento.ShoppingCartProductEntity[] products, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartProductAdd(sessionId, quoteId, products, storeId);
  }
  
  public boolean shoppingCartProductUpdate(java.lang.String sessionId, int quoteId, Magento.ShoppingCartProductEntity[] products, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartProductUpdate(sessionId, quoteId, products, storeId);
  }
  
  public boolean shoppingCartProductRemove(java.lang.String sessionId, int quoteId, Magento.ShoppingCartProductEntity[] products, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartProductRemove(sessionId, quoteId, products, storeId);
  }
  
  public Magento.CatalogProductEntity[] shoppingCartProductList(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartProductList(sessionId, quoteId, storeId);
  }
  
  public boolean shoppingCartProductMoveToCustomerQuote(java.lang.String sessionId, int quoteId, Magento.ShoppingCartProductEntity[] products, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartProductMoveToCustomerQuote(sessionId, quoteId, products, storeId);
  }
  
  public boolean shoppingCartCustomerSet(java.lang.String sessionId, int quoteId, Magento.ShoppingCartCustomerEntity customer, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartCustomerSet(sessionId, quoteId, customer, storeId);
  }
  
  public boolean shoppingCartCustomerAddresses(java.lang.String sessionId, int quoteId, Magento.ShoppingCartCustomerAddressEntity[] customer, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartCustomerAddresses(sessionId, quoteId, customer, storeId);
  }
  
  public boolean shoppingCartShippingMethod(java.lang.String sessionId, int quoteId, java.lang.String method, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartShippingMethod(sessionId, quoteId, method, storeId);
  }
  
  public Magento.ShoppingCartShippingMethodEntity[] shoppingCartShippingList(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartShippingList(sessionId, quoteId, storeId);
  }
  
  public boolean shoppingCartPaymentMethod(java.lang.String sessionId, int quoteId, Magento.ShoppingCartPaymentMethodEntity method, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartPaymentMethod(sessionId, quoteId, method, storeId);
  }
  
  public Magento.ShoppingCartPaymentMethodResponseEntityArray shoppingCartPaymentList(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartPaymentList(sessionId, quoteId, storeId);
  }
  
  public boolean shoppingCartCouponAdd(java.lang.String sessionId, int quoteId, java.lang.String couponCode, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartCouponAdd(sessionId, quoteId, couponCode, storeId);
  }
  
  public boolean shoppingCartCouponRemove(java.lang.String sessionId, int quoteId, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.shoppingCartCouponRemove(sessionId, quoteId, storeId);
  }
  
  public Magento.GiftMessageResponse giftMessageSetForQuote(java.lang.String sessionId, java.lang.String quoteId, Magento.GiftMessageEntity giftMessage, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.giftMessageSetForQuote(sessionId, quoteId, giftMessage, storeId);
  }
  
  public Magento.GiftMessageResponse giftMessageSetForQuoteItem(java.lang.String sessionId, java.lang.String quoteItemId, Magento.GiftMessageEntity giftMessage, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.giftMessageSetForQuoteItem(sessionId, quoteItemId, giftMessage, storeId);
  }
  
  public Magento.GiftMessageResponse[] giftMessageSetForQuoteProduct(java.lang.String sessionId, java.lang.String quoteId, Magento.GiftMessageAssociativeProductsEntity[] productsAndMessages, java.lang.String storeId) throws java.rmi.RemoteException{
    if (mage_Api_Model_Server_V2_HandlerPortType == null)
      _initMage_Api_Model_Server_V2_HandlerPortTypeProxy();
    return mage_Api_Model_Server_V2_HandlerPortType.giftMessageSetForQuoteProduct(sessionId, quoteId, productsAndMessages, storeId);
  }
  
  
}