package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerConstructorMethodTest {

	@Test
	public void success() {
		Broker broker = new Broker("BR01", "WeExplore", "999111999", "111999111", "BK01987654321");

		Assert.assertEquals("BR01", broker.getCode());
		Assert.assertEquals("WeExplore", broker.getName());
		Assert.assertEquals("999111999", broker.getBuyerNIF());
		Assert.assertEquals("111999111", broker.getSellerNIF());
		Assert.assertEquals("BK01987654321", broker.getIBAN());
		Assert.assertEquals(0, broker.getNumberOfAdventures());
		Assert.assertTrue(Broker.brokers.contains(broker));
	}

	@Test
	public void nullCode() {
		try {
			new Broker(null, "WeExplore", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new Broker("", "WeExplore", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new Broker("  ", "WeExplore", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueCode() {
		Broker broker = new Broker("BR01", "WeExplore", "999111999", "111999111", "BK01987654321");

		try {
			new Broker("BR01", "WeExploreX", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void nullName() {
		try {
			new Broker("BR01", null, "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyName() {
		try {
			new Broker("BR01", "", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankName() {
		try {
			new Broker("BR01", "    ", "999111999", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void nullBuyerNIF() {
		try {
			new Broker("BR01", "WeExplore", null, "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyBuyerNIF() {
		try {
			new Broker("BR01", "WeExplore", "", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankBuyerNIF() {
		try {
			new Broker("BR01", "WeExplore", "  ", "111999111", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void uniqueBuyerNIF() {
		Broker broker = new Broker("BR01", "WeExplore", "999111999", "111999111", "BK01987654321");

		try {
			new Broker("BR02", "WeExploreX", "999111999", "111999112", "BK01987654322");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}
	
	@Test
	public void nullSellerNIF() {
		try {
			new Broker("BR01", "WeExplore", "999111999", null, "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptySellerNIF() {
		try {
			new Broker("BR01", "WeExplore", "999111999", "", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankSellerNIF() {
		try {
			new Broker("BR01", "WeExplore", "999111999", "  ", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void uniqueSellerNIF() {
		Broker broker = new Broker("BR01", "WeExplore", "999111999", "111999111", "BK01987654321");

		try {
			new Broker("BR03", "WeExploreX", "999111993", "111999111", "BK01987654323");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}
	
	@Test
	public void nullIBAN() {
		try {
			new Broker("BR01", "WeExplore", "999111999", "111999111", null);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyIBAN() {
		try {
			new Broker("BR01", "WeExplore", "999111999", "111999111", "");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankIBAN() {
		try {
			new Broker("BR01", "WeExplore", "999111999", "111999111", "  ");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void uniqueIBAN() {
		Broker broker = new Broker("BR01", "WeExplore", "999111999", "111999111", "BK01987654321");

		try {
			new Broker("BR04", "WeExploreX", "999111994", "111999114", "BK01987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}
