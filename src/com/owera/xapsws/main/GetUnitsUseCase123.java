package com.owera.xapsws.main;

import com.owera.xapsws.GetUnitsRequest;
import com.owera.xapsws.GetUnitsResponse;
import com.owera.xapsws.Login;
import com.owera.xapsws.Parameter;
import com.owera.xapsws.ParameterList;
import com.owera.xapsws.Unit;
import com.owera.xapsws.XAPSWSProxy;

public class GetUnitsUseCase123 {

	public static void main(String[] args) {
		try {
			XAPSWSProxy tp = new XAPSWSProxy();
			//			tp.setEndpoint("http://pingcom.xaps-hosting.net/xapsws/services/xAPSWS");
			tp.setEndpoint("http://pingcom.xaps-hosting.net/xapsws/redirect");
			//			tp.setEndpoint("http://xaps-a.owera.com/xapsws/services/xAPSWS");
			Unit unit = new Unit();
			//			Unittype ut = new Unittype();
			//			ut.setName("NPA201E-Cellip");
			//			Profile p = new Profile();
			//			p.setName("Default");
			//			unit.setUnitId("AB");
			//			unit.setSerialNumber("0021%");
			unit.setUnitId("002194-NPA201E-00219401CE2E");
			//			unit.setSerialNumber("%");
			//			Parameter[] parameterArr = new Parameter[1];
			//			parameterArr[0] = new Parameter("System.X_OWERA-COM.Device.MAC", "012345678901", "EQ");
			//			parameterArr[1] = new Parameter("System.X_TEST_OWERA-COM.Place", "Stavanger", "E");
			//			ParameterList parameters = new ParameterList(parameterArr);
			//			unit.setParameters(parameters);
			//			unit.setUnittype(ut);
			//			unit.setProfile(p);
			//			Login login = new Login("teamf1", "teamf1123");
			Login login = new Login("cellip", "cellip123");
			GetUnitsRequest auReq = new GetUnitsRequest(login, unit);
			GetUnitsResponse auRes = tp.getUnits(auReq);
			System.out.println("More units: " + auRes.isMoreUnits());
			if (auRes.getUnits() != null && auRes.getUnits().getUnitArray() != null) {
				int unitCounter = 0;
				for (Unit u : auRes.getUnits().getUnitArray()) {
					System.out.println(unitCounter + " UnitId: " + u.getUnitId());
					ParameterList parameterList = u.getParameters();
					if (parameterList != null && parameterList.getParameterArray() != null) {
						int counter = 0;
						for (Parameter param : parameterList.getParameterArray()) {
							System.out.println(unitCounter + " Parameter-" + counter + ": " + param.getName() + " = " + param.getValue() + " (" + param.getFlags() + ")");
							counter++;
						}
					}
					unitCounter++;
				}
			}
		} catch (Throwable t) {
			System.err.println("Throwable: " + t);
		}

	}
}
