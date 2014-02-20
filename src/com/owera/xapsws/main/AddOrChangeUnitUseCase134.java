package com.owera.xapsws.main;

import com.owera.xapsws.AddOrChangeUnitRequest;
import com.owera.xapsws.AddOrChangeUnitResponse;
import com.owera.xapsws.Login;
import com.owera.xapsws.Parameter;
import com.owera.xapsws.ParameterList;
import com.owera.xapsws.Profile;
import com.owera.xapsws.Unit;
import com.owera.xapsws.Unittype;
import com.owera.xapsws.XAPSWSProxy;

public class AddOrChangeUnitUseCase134 {

	public static void main(String[] args) {
		try {
			XAPSWSProxy tp = new XAPSWSProxy();
			// Choose 1 out of 2 options:
			// Send traffic to redirect-URL - the Web Service Server will log XML to file
			tp.setEndpoint("http://localhost/xapsws/redirect");
			// Send traffic directly to Web Service Server:
			// tp.setEndpoint("http://localhost/xapsws/services/xAPSWS");
			
			// Populate data on unit, profile and unittype, add two parameters to set on the unit
			Unit unit = new Unit();
			unit.setSerialNumber("012345678902");
			Profile p = new Profile();
			p.setName("Default");
			Unittype ut = new Unittype();
			ut.setName("NPA201E");

			Parameter[] parameterArr = new Parameter[2];
			parameterArr[0] = new Parameter("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.SIP.AuthPassword", "freyrsSipPassword", null);
			parameterArr[1] = new Parameter("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.SIP.AuthUserName", "freyrsSipUsername", "AC");
			ParameterList parameters = new ParameterList(parameterArr);
			
			unit.setParameters(parameters);
			unit.setProfile(p);
			unit.setUnittype(ut);
			
			// Populate login object
			Login login = new Login("user", "pass");
			
			// Perform login and service
			AddOrChangeUnitRequest auReq = new AddOrChangeUnitRequest(login, unit);
			
			// Response received
			AddOrChangeUnitResponse auRes = tp.addOrChangeUnit(auReq);
			
			// Dump some data to sysout, loop through parameter list of unit
			System.out.println("UnitId: " + auRes.getUnit().getUnitId());
			ParameterList parameterList = auRes.getUnit().getParameters();
			if (parameterList != null && parameterList.getParameterArray() != null) {
				int counter = 0;
				for (Parameter param : parameterList.getParameterArray()) {
					System.out.println("Parameter-" + counter + ": " + param.getName() + " = " + param.getValue() + " (" + param.getFlags() + ")");
					counter++;
				}
			}
		} catch (Throwable t) {
			System.err.println("Throwable: " + t);
			t.printStackTrace();
		}

	}

}
