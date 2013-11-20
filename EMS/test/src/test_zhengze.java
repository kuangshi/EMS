public class test_zhengze {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "¸Ö½î ÕýÈ· ÊÇ";
		String[] string = str.trim().split(" ");
		str= string[0];
		for (int i = 1; i < string.length; i++) {
			str = str+"%"+string[i];
		}
		System.out.println(str);
	}

}
