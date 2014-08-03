package org.eclipse.swt.internal.ole.win32;

public class IDataObject extends IUnknown
{
  public IDataObject(int paramInt)
  {
    super(paramInt);
  }

  public int EnumFormatEtc(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(8, this.address, paramInt, paramArrayOfInt);
  }

  public int GetData(FORMATETC paramFORMATETC, STGMEDIUM paramSTGMEDIUM)
  {
    return COM.VtblCall(3, this.address, paramFORMATETC, paramSTGMEDIUM);
  }

  public int GetDataHere(FORMATETC paramFORMATETC, STGMEDIUM paramSTGMEDIUM)
  {
    return COM.VtblCall(4, this.address, paramFORMATETC, paramSTGMEDIUM);
  }

  public int QueryGetData(FORMATETC paramFORMATETC)
  {
    return COM.VtblCall(5, this.address, paramFORMATETC);
  }

  public int SetData(FORMATETC paramFORMATETC, STGMEDIUM paramSTGMEDIUM, boolean paramBoolean)
  {
    return COM.VtblCall(7, this.address, paramFORMATETC, paramSTGMEDIUM, paramBoolean);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IDataObject
 * JD-Core Version:    0.6.2
 */