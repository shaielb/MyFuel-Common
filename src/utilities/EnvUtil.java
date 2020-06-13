package utilities;

public class EnvUtil {

	/**
	 * @return
	 */
	public static int getCoresNumber() {
		return Runtime.getRuntime().availableProcessors();
	}
}
