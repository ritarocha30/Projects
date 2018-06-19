package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.Assert;
import org.junit.Test;
import pt.ist.fenixframework.FenixFramework;

public class IRSGetIRSMethodTest extends RollbackTestAbstractClass {

    @Override
    public void populate4Test() {

    }

    @Test
    public void success(){
        IRS irs = IRS.getIRS();
        Assert.assertNotNull(irs);
        Assert.assertEquals(irs, FenixFramework.getDomainRoot().getIRS());
    }

    @Test
    public void alreadyExists(){
        IRS test = IRS.getIRS();
        IRS test1 = IRS.getIRS();
        Assert.assertNotNull(test);
        Assert.assertNotNull(test1);
        Assert.assertEquals(test.getOid(), test1.getOid());
    }

}
