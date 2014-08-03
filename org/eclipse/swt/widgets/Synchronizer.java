/*       */ package org.eclipse.swt.widgets;
/*       */ 
/*       */ import org.eclipse.swt.SWT;
/*       */ import org.eclipse.swt.graphics.Device;
/*       */ import org.eclipse.swt.internal.Compatibility;
/*       */ 
/*       */ public class Synchronizer
/*       */ {
/*       */   Display display;
/*       */   int messageCount;
/*       */   RunnableLock[] messages;
/*       */   Object messageLock = new Object();
/*       */   Thread syncThread;
/*       */   static final int GROW_SIZE = 4;
/*       */   static final int MESSAGE_LIMIT = 64;
/*       */   static final boolean IS_CARBON = "carbon".equals(SWT.getPlatform());
/*       */   static final boolean IS_GTK = "gtk".equals(SWT.getPlatform());
/*       */ 
/*       */   public Synchronizer(Display paramDisplay)
/*       */   {
/*       */     this.display = paramDisplay;
/*       */   }
/*       */ 
/*       */   void addLast(RunnableLock paramRunnableLock)
/*       */   {
/*       */     int i = 0;
/*       */     synchronized (this.messageLock)
/*       */     {
/*       */       if (this.messages == null)
/*       */         this.messages = new RunnableLock[4];
/*       */       if (this.messageCount == this.messages.length)
/*       */       {
/*       */         RunnableLock[] arrayOfRunnableLock = new RunnableLock[this.messageCount + 4];
/*       */         System.arraycopy(this.messages, 0, arrayOfRunnableLock, 0, this.messageCount);
/*       */         this.messages = arrayOfRunnableLock;
/*       */       }
/*       */       this.messages[(this.messageCount++)] = paramRunnableLock;
/*       */       i = this.messageCount == 1 ? 1 : 0;
/*       */     }
/*       */     if (i != 0)
/*       */       this.display.wakeThread();
/*       */   }
/*       */ 
/*       */   protected void asyncExec(Runnable paramRunnable)
/*       */   {
/*       */     if ((paramRunnable == null) && (!IS_CARBON) && (!IS_GTK))
/*       */     {
/*       */       this.display.wake();
/*       */       return;
/*       */     }
/*       */     addLast(new RunnableLock(paramRunnable));
/*       */   }
/*       */ 
/*       */   int getMessageCount()
/*       */   {
/*       */     synchronized (this.messageLock)
/*       */     {
/*       */       return this.messageCount;
/*       */     }
/*       */   }
/*       */ 
/*       */   void releaseSynchronizer()
/*       */   {
/*       */     this.display = null;
/*       */     this.messages = null;
/*       */     this.messageLock = null;
/*       */     this.syncThread = null;
/*       */   }
/*       */ 
/*       */   RunnableLock removeFirst()
/*       */   {
/*       */     synchronized (this.messageLock)
/*       */     {
/*       */       if (this.messageCount == 0)
/*       */         return null;
/*       */       RunnableLock localRunnableLock = this.messages[0];
/*       */       System.arraycopy(this.messages, 1, this.messages, 0, --this.messageCount);
/*       */       this.messages[this.messageCount] = null;
/*       */       if ((this.messageCount == 0) && (this.messages.length > 64))
/*       */         this.messages = null;
/*       */       return localRunnableLock;
/*       */     }
/*       */   }
/*       */ 
/*       */   boolean runAsyncMessages()
/*       */   {
/*       */     return runAsyncMessages(false);
/*       */   }
/*       */ 
/*       */   boolean runAsyncMessages(boolean paramBoolean)
/*       */   {
/*       */     boolean bool = false;
/*       */     do
/*       */     {
/*       */       RunnableLock localRunnableLock = removeFirst();
/*       */       if (localRunnableLock == null)
/*       */         return bool;
/*       */       bool = true;
/*       */       synchronized (localRunnableLock)
/*       */       {
/*       */         this.syncThread = localRunnableLock.thread;
/*       */         try
/*       */         {
/*       */           localRunnableLock.run();
/*       */         }
/*       */         catch (Throwable localThrowable)
/*       */         {
/*       */           localRunnableLock.throwable = localThrowable;
/*       */           SWT.error(46, localThrowable);
/*       */         }
/*       */         finally
/*       */         {
/*       */           this.syncThread = null;
/*       */           localRunnableLock.notifyAll();
/*       */           ret;
/*       */         }
/*       */       }
/*       */     }
/*       */     while (paramBoolean);
/*       */     return bool;
/*       */   }
/*       */ 
/*       */   protected void syncExec(Runnable paramRunnable)
/*       */   {
/*       */     RunnableLock localRunnableLock = null;
/*       */     synchronized (Device.class)
/*       */     {
/*       */       if ((this.display == null) || (this.display.isDisposed()))
/*       */         SWT.error(45);
/*       */       if (!this.display.isValidThread())
/*       */       {
/*       */         if (paramRunnable == null)
/*       */         {
/*       */           this.display.wake();
/*       */           return;
/*       */         }
/*       */         localRunnableLock = new RunnableLock(paramRunnable);
/*       */         localRunnableLock.thread = Thread.currentThread();
/*       */         addLast(localRunnableLock);
/*       */       }
/*       */     }
/*       */     if (localRunnableLock == null)
/*       */     {
/*       */       if (paramRunnable != null)
/*       */         paramRunnable.run();
/*       */       return;
/*       */     }
/*       */     synchronized (localRunnableLock)
/*       */     {
/*       */       int i = 0;
/*       */       while (!localRunnableLock.done())
/*       */         try
/*       */         {
/*       */           localRunnableLock.wait();
/*       */         }
/*       */         catch (InterruptedException localInterruptedException)
/*       */         {
/*       */           i = 1;
/*       */         }
/*       */       if (i != 0)
/*       */         Compatibility.interrupt();
/*       */       if (localRunnableLock.throwable != null)
/*       */         SWT.error(46, localRunnableLock.throwable);
/*       */     }
/*       */   }
/*       */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Synchronizer
 * JD-Core Version:    0.6.2
 */