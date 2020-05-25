package utilities;

public class EnvUtil {

	public static int getCoresNumber() {
		return Runtime.getRuntime().availableProcessors();
	}
}
