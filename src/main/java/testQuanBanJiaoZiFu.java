
public class testQuanBanJiaoZiFu {
	public static void main(String[] args)
	{
		/*
		ȫ��---ָһ���ַ�ռ��������׼�ַ�λ�á� 
		�����ַ��͹涨��ȫ�ǵ�Ӣ���ַ�������GB2312-80�е�ͼ�η��ź������ַ�����ȫ���ַ���һ���ϵͳ�����ǲ���ȫ���ַ��ģ�ֻ���������ִ���ʱ�Ż�ʹ��ȫ���ַ��� 
		���---ָһ�ַ�ռ��һ����׼���ַ�λ�á� 
		ͨ����Ӣ����ĸ�����ּ������ż����ǰ�ǵģ���ǵ���ʾ���붼��һ���ֽڡ���ϵͳ�ڲ������������ַ�����Ϊ�������봦���ģ������û���������Ͳ���ʱһ�㶼ʹ�ð��.ȫ��ռ�����ֽڣ����ռһ���ֽڡ� 
		���ȫ����Ҫ����Ա�������˵�ģ�ȫ�Ǳ��ռ�����ֽڣ����ռһ���ֽڣ��������ǰ�ǻ���ȫ�ǣ����ֶ�����Ҫռ�����ֽ� 
		�ڱ�����Դ������ֻ��ʹ�ð�Ǳ�㣨�������ַ����ڲ������ݣ� 
		�ڲ�֧�ֺ��ֵ����Եļ������ֻ��ʹ�ð�Ǳ��. 
		���磺�ڰ��״̬�´�ľ��ֻ��һ��Բ�㣬����ȫ���¾��Ǳ�׼�ľ�š�
		
		*/
		String halfChar=",. ";
		String halfZhongChar="���� ";  //�������״̬������Ķ��ź;����ȫ�Ƿ��ţ��ո��ǰ�Ƿ���
		String quanZhongChar="������";//�������״̬������Ķ��ź;�ţ��ո���ȫ�Ƿ��ţ�
		
		//ע Ӣ���޶ٺ�
		for(int i=0;i<halfChar.length();i++)
		{
			int i_1=(int)halfChar.charAt(i);
			int i_2=(int)halfZhongChar.charAt(i);
			int i_3=(int)quanZhongChar.charAt(i);
			System.out.println(i_1+"|"+i_2+"|"+i_3);
			
		}
	}

}