package com.owera.xapsws.main;

import com.owera.xapsws.DeleteUnitRequest;
import com.owera.xapsws.DeleteUnitResponse;
import com.owera.xapsws.Login;
import com.owera.xapsws.Profile;
import com.owera.xapsws.Unit;
import com.owera.xapsws.Unittype;
import com.owera.xapsws.XAPSWSProxy;

/**
 * Should be run after AddOrChangeUnitUseCase2 - we will try to delete the unit made there
 * @author Morten
 *
 */
public class DeleteUnitUseCase12 {

	public static void main(String[] args) {
		try {
			XAPSWSProxy tp = new XAPSWSProxy();
			tp.setEndpoint("http://localhost:8080/xapsws/services/xAPSWS");
			Unit unit = new Unit();
			Unittype ut = new Unittype();
			ut.setName("NPA201E-Cellip");
			Profile p = new Profile();
			p.setName("Default");
			unit.setUnittype(ut);
			unit.setProfile(p);
			unit.setUnitId("000000-TR069TestClient-000000000001");
//			unit.setSerialNumber("012345678901");
			Login login = new Login("cellip", "cellip123");
			DeleteUnitRequest auReq = new DeleteUnitRequest(login, unit);
			DeleteUnitResponse auRes = tp.deleteUnit(auReq);
			System.out.println("Deleted: " + auRes.isDeleted());
		} catch (Throwable t) {
			System.err.println("Throwable: " + t);
		}

	}

}
