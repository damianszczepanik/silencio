package pl.szczepanik.silencio.utils;

/**
 * @author Damian Szczepanik <damianszczepanik@github>
 */
public final class CommonUtility {

    /** Does not allow to create instance of utility class. */
    private CommonUtility() {
    }

    /**
     * Implementation of {@link #wait(long)} method that does suppress exception (if occurs).
     * 
     * @param timeout
     *            timeout the maximum time to wait in milliseconds
     */
    public static void saveWait(long timeout) {
        Thread currentThread = Thread.currentThread();
        synchronized (currentThread) {
            try {
                currentThread.wait(timeout);
            } catch (InterruptedException | IllegalArgumentException e) {
            }
        }
    }
}
