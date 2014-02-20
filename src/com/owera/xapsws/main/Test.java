package com.owera.xapsws.main;

import com.owera.xapsws.AddOrChangeProfileRequest;
import com.owera.xapsws.AddOrChangeProfileResponse;
import com.owera.xapsws.AddOrChangeUnittypeRequest;
import com.owera.xapsws.AddOrChangeUnittypeResponse;
import com.owera.xapsws.DeleteProfileRequest;
import com.owera.xapsws.DeleteProfileResponse;
import com.owera.xapsws.GetProfilesRequest;
import com.owera.xapsws.GetProfilesResponse;
import com.owera.xapsws.GetUnitIdsRequest;
import com.owera.xapsws.GetUnitIdsResponse;
import com.owera.xapsws.GetUnittypesRequest;
import com.owera.xapsws.GetUnittypesResponse;
import com.owera.xapsws.Login;
import com.owera.xapsws.Parameter;
import com.owera.xapsws.ParameterList;
import com.owera.xapsws.Profile;
import com.owera.xapsws.Unit;
import com.owera.xapsws.Unittype;
import com.owera.xapsws.XAPSWSProxy;

public class Test {

	public static void main(String[] args) {
		try {
			XAPSWSProxy proxy = new XAPSWSProxy();
			proxy.setEndpoint("http://localhost/xapsws/redirect");
			Login login = new Login("user", "pass");

			// Standard names used in this test
			String unittypeName = "WSTest";

			/* GET/DELETE */
			// List unittypes
			GetUnittypesResponse gutRes = proxy.getUnittypes(new GetUnittypesRequest(login, null));
			Unittype[] utArray = gutRes.getUnittypes().getUnittypeArray();
			System.out.println("Found " + utArray.length + " unittypes");
			for (Unittype ut : utArray) {
				System.out.println("Found " + ut.getName() + " with " + ut.getParameters().getParameterArray().length + " parameters");
				if (ut.getName().equals(unittypeName)) {
					// List profiles
					GetProfilesResponse gpRes = proxy.getProfiles(new GetProfilesRequest(login, ut, null));
					Profile[] pArray = gpRes.getProfiles().getProfileArray();
					System.out.println("Found " + pArray.length + " profiles");
					// List units
					GetUnitIdsResponse guiRes = proxy.getUnitIds(new GetUnitIdsRequest(login, new Unit()));
					System.out.println("Found " + guiRes.getUnits().getUnitIdArray().length + " units");
					// Delete profiles 
					for (Profile p : pArray) {
						DeleteProfileResponse dpRes = proxy.deleteProfile(new DeleteProfileRequest(login, ut, p));
						if (dpRes.isDeleted())
							System.out.println("Deleted" + p.getName() + " profile");
						else
							throw new RuntimeException("Profile " + p.getName() + " was not deleted");
					}
					// Delete unittype - cannot do this with permissions set on this unittype 
					//					DeleteUnittypeResponse dutRes = proxy.deleteUnittype(new DeleteUnittypeRequest(login, unittypeName));
					//					if (dutRes.isDeleted())
					//						System.out.println("Deleted " + unittypeName + " unittype");
					//					else
					//						throw new RuntimeException("Unittype " + unittypeName + " was not deleted");
				}
			}

			/* ADD/CHANGE */
			// Add unittype
			Unittype unittype = new Unittype(unittypeName, null, "Pingcom", null, "TR-069", null);
			//			Unittype unittype = new Unittype(unittypeName, "000000", "Owera", "Test unittype", "TR-069", null);
			AddOrChangeUnittypeResponse aocutRes = proxy.addOrChangeUnittype(new AddOrChangeUnittypeRequest(login, unittype));
			if (aocutRes.getUnittype() != null) {
				System.out.println("Added " + unittypeName + " unittype, no parameters yet");
				Profile profile = new Profile("Default", null);
				// Add profile
				AddOrChangeProfileResponse aocpRes = proxy.addOrChangeProfile(new AddOrChangeProfileRequest(login, unittype, profile));
				if (aocpRes.getProfile() != null)
					System.out.println("Added " + profile + " profile, no parameters yet");
				else
					throw new RuntimeException("Profile Default was not added");
				// Change unittype + unittype parameters
				unittype.setVendor("PingCom");
				Parameter[] utpArray = new Parameter[5];
				utpArray[0] = new Parameter("A.B.C", "RW", null);
				utpArray[1] = new Parameter("A.B.D", "RW", null);
				utpArray[2] = new Parameter("A.B.E", "SDRW", null);
				utpArray[3] = new Parameter("A.B.F", "SDRW", "AC");
				utpArray[4] = new Parameter("A.B.G", "SDRW", "AC");
				unittype.setParameters(new ParameterList(utpArray));
				aocutRes = proxy.addOrChangeUnittype(new AddOrChangeUnittypeRequest(login, unittype));
				if (aocutRes.getUnittype() != null) {
					System.out.println("Changed " + unittypeName + " unittype, " + unittype.getParameters().getParameterArray().length + " parameters");
				} else {
					throw new RuntimeException("Unittype " + unittypeName + " was not changed");
				}
				utpArray = new Parameter[2];
				utpArray[0] = new Parameter("A.B.C", "RW", "D");
				utpArray[1] = new Parameter("A.B.D", null, "D");
				unittype.setParameters(new ParameterList(utpArray));
				// Delete unittype parameters
				aocutRes = proxy.addOrChangeUnittype(new AddOrChangeUnittypeRequest(login, unittype));
				if (aocutRes.getUnittype() != null) {
					System.out.println("Delete unittype parameters from  " + unittypeName + " unittype, " + unittype.getParameters().getParameterArray().length + " parameters");
				} else {
					throw new RuntimeException("Unittype " + unittypeName + " was not changed");
				}
				// Add profile parameters
				Parameter[] pArray = new Parameter[3];
				pArray[0] = new Parameter("A.B.E", "AAA", null);
				pArray[1] = new Parameter("A.B.F", "AAAA", "AC");
				pArray[2] = new Parameter("A.B.G", "AAAAA", "AC");
				profile.setParameters(new ParameterList(pArray));
				aocpRes = proxy.addOrChangeProfile(new AddOrChangeProfileRequest(login, unittype, profile));
				if (aocpRes.getProfile() != null)
					System.out.println("Changed " + profile + " profile parameters");
				else
					throw new RuntimeException("Profile Default could not be changed (tried to add new parameters)");
				// Delete profile parameters
				pArray = new Parameter[2];
				pArray[0] = new Parameter("A.B.E", "AAA", "D");
				pArray[1] = new Parameter("A.B.F", null, "D");
				profile.setParameters(new ParameterList(pArray));
				aocpRes = proxy.addOrChangeProfile(new AddOrChangeProfileRequest(login, unittype, profile));
				if (aocpRes.getProfile() != null)
					System.out.println("Delete profile parameters from profile" + profile);
				else
					throw new RuntimeException("Profile Default could not be changed (tried to delete parameters)");
				// Add units

			} else
				throw new RuntimeException("Unittype " + unittypeName + " was not added");
		} catch (Throwable t) {
			System.err.println("Throwable: " + t);
			t.printStackTrace();
		}

	}
}
