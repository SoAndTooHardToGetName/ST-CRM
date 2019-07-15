package tools;

public class Datacheck {
	public static boolean pass(String pass) {
		if (pass == null || pass.length() < 6)
			return true;
		return false;
	}

	public static boolean Str(String str) {
		if (str == null || str.trim().isEmpty())
			return true;
		return false;
	}

	public static boolean time(String time) {
		String timeformat = "[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
		if (time == null || !time.matches(timeformat))
			return true;
		return false;
	}

}
