package com.owera.xapsws;

public class XAPSWSProxy implements com.owera.xapsws.XAPSWS_PortType {
  private String _endpoint = null;
  private com.owera.xapsws.XAPSWS_PortType xAPSWS_PortType = null;
  
  public XAPSWSProxy() {
    _initXAPSWSProxy();
  }
  
  public XAPSWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initXAPSWSProxy();
  }
  
  private void _initXAPSWSProxy() {
    try {
      xAPSWS_PortType = (new com.owera.xapsws.XAPSWS_ServiceLocator()).getxAPSWS();
      if (xAPSWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)xAPSWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)xAPSWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (xAPSWS_PortType != null)
      ((javax.xml.rpc.Stub)xAPSWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.owera.xapsws.XAPSWS_PortType getXAPSWS_PortType() {
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType;
  }
  
  public com.owera.xapsws.GetUnittypesResponse getUnittypes(com.owera.xapsws.GetUnittypesRequest getUnittypesRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.getUnittypes(getUnittypesRequest);
  }
  
  public com.owera.xapsws.AddOrChangeUnittypeResponse addOrChangeUnittype(com.owera.xapsws.AddOrChangeUnittypeRequest addOrChangeUnittypeRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.addOrChangeUnittype(addOrChangeUnittypeRequest);
  }
  
  public com.owera.xapsws.DeleteUnittypeResponse deleteUnittype(com.owera.xapsws.DeleteUnittypeRequest deleteUnittypeRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.deleteUnittype(deleteUnittypeRequest);
  }
  
  public com.owera.xapsws.GetProfilesResponse getProfiles(com.owera.xapsws.GetProfilesRequest getProfilesRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.getProfiles(getProfilesRequest);
  }
  
  public com.owera.xapsws.AddOrChangeProfileResponse addOrChangeProfile(com.owera.xapsws.AddOrChangeProfileRequest addOrChangeProfileRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.addOrChangeProfile(addOrChangeProfileRequest);
  }
  
  public com.owera.xapsws.DeleteProfileResponse deleteProfile(com.owera.xapsws.DeleteProfileRequest deleteProfileRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.deleteProfile(deleteProfileRequest);
  }
  
  public com.owera.xapsws.GetUnitsResponse getUnits(com.owera.xapsws.GetUnitsRequest getUnitsRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.getUnits(getUnitsRequest);
  }
  
  public com.owera.xapsws.AddOrChangeUnitResponse addOrChangeUnit(com.owera.xapsws.AddOrChangeUnitRequest addOrChangeUnitRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.addOrChangeUnit(addOrChangeUnitRequest);
  }
  
  public com.owera.xapsws.DeleteUnitResponse deleteUnit(com.owera.xapsws.DeleteUnitRequest deleteUnitRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.deleteUnit(deleteUnitRequest);
  }
  
  public com.owera.xapsws.GetUnitIdsResponse getUnitIds(com.owera.xapsws.GetUnitIdsRequest getUnitIdsRequest) throws java.rmi.RemoteException{
    if (xAPSWS_PortType == null)
      _initXAPSWSProxy();
    return xAPSWS_PortType.getUnitIds(getUnitIdsRequest);
  }
  
  
}