package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;

public abstract class Resource
{
  Device device;

  public Resource()
  {
  }

  Resource(Device paramDevice)
  {
    if (paramDevice == null)
      paramDevice = Device.getDevice();
    if (paramDevice == null)
      SWT.error(4);
    this.device = paramDevice;
  }

  void destroy()
  {
  }

  public void dispose()
  {
    if (this.device == null)
      return;
    if (this.device.isDisposed())
      return;
    destroy();
    if (this.device.tracking)
      this.device.dispose_Object(this);
    this.device = null;
  }

  public Device getDevice()
  {
    Device localDevice = this.device;
    if ((localDevice == null) || (isDisposed()))
      SWT.error(44);
    return localDevice;
  }

  void init()
  {
    if (this.device.tracking)
      this.device.new_Object(this);
  }

  public abstract boolean isDisposed();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Resource
 * JD-Core Version:    0.6.2
 */