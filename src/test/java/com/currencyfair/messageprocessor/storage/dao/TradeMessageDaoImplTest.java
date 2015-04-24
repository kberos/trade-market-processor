package com.currencyfair.messageprocessor.storage.dao;

import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessage;
import com.currencyfair.messageprocessor.model.CountryEnum;
import com.currencyfair.messageprocessor.model.CurrencyEnum;
import com.currencyfair.messageprocessor.model.TradeMessage;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import junit.framework.TestCase;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;


@RunWith(SpringJUnit4ClassRunner.class)

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@ContextConfiguration({"classpath:spring/storage-test-context.xml"})
@ActiveProfiles("test")
public class TradeMessageDaoImplTest extends TestCase {

    @Autowired
    TradeMessageDao tradeMessageDao;

    @ClassRule
    public static ManagedMongoDb managedMongoDb = newManagedMongoDbRule()
            .mongodPath(
                    "/usr/local/Cellar/mongodb/3.0.2")
            .build();

    //InMemoryMongoDb inMemoryMongoDb = new InMemoryMongoDb();

//    @Rule
//    public MongoDbRule remoteMongoDbRule = new MongoDbRule(inMemoryMongoDb().databaseName("processor").build());

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultManagedMongoDb("processor");

//    @UsingDataSet(locations="initialData.json", loadStrategy= LoadStrategyEnum.CLEAN_INSERT)
    //@ShouldMatchDataSet(location="expectedData.json")
    @Test
    public void testStoreMessage() throws Exception {
        TradeMessage tradeMessage = new TradeMessage();
        tradeMessage.setAmountBuy(1111.0);
        tradeMessage.setAmountSell(2222.0);
        tradeMessage.setRate(0.74);
        tradeMessage.setUserId("4");
        tradeMessage.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessage.setOriginatingCountry(CountryEnum.GB);
        tradeMessageDao.storeMessage(tradeMessage);
        List<TradeMessage> tradeMessageList = tradeMessageDao.getAllMessages();
        assertEquals(1, tradeMessageList.size());
        assertEquals(tradeMessage,tradeMessageList.get(0));
    }

    @Test
   @UsingDataSet(loadStrategy= LoadStrategyEnum.DELETE_ALL)
    public void testGetAllMessages() throws Exception {
        List<TradeMessage> tradeMessageList = tradeMessageDao.getAllMessages();
        assertEquals(0,tradeMessageList.size());

        TradeMessage tradeMessage = new TradeMessage();
        tradeMessage.setAmountBuy(1111.0);
        tradeMessage.setAmountSell(2222.0);
        tradeMessage.setRate(0.74);
        tradeMessage.setUserId("4");
        tradeMessage.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessage.setOriginatingCountry(CountryEnum.GB);

        TradeMessage tradeMessage2 = new TradeMessage();
        tradeMessage2.setAmountBuy(1111.0);
        tradeMessage2.setAmountSell(2222.0);
        tradeMessage2.setRate(0.74);
        tradeMessage2.setUserId("4");
        tradeMessage2.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage2.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessage2.setOriginatingCountry(CountryEnum.GB);
        tradeMessageDao.storeMessage(tradeMessage);
        tradeMessageDao.storeMessage(tradeMessage2);
        tradeMessageList = tradeMessageDao.getAllMessages();
        assertEquals(2,tradeMessageList.size());

    }

    @Test
    @UsingDataSet(loadStrategy= LoadStrategyEnum.DELETE_ALL)
    public void testGetVolumes() throws Exception {
       List<AnalyticsVolumeMessage> volumeMessageList =  tradeMessageDao.getVolumes();
        assertEquals(0,volumeMessageList.size());

        TradeMessage tradeMessage = new TradeMessage();
        tradeMessage.setAmountBuy(100.0);
        tradeMessage.setAmountSell(200.0);
        tradeMessage.setRate(0.74);
        tradeMessage.setUserId("4");
        tradeMessage.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessage.setOriginatingCountry(CountryEnum.GB);
        tradeMessageDao.storeMessage(tradeMessage);

        TradeMessage tradeMessage2 = new TradeMessage();
        tradeMessage2.setAmountBuy(150.0);
        tradeMessage2.setAmountSell(200.0);
        tradeMessage2.setRate(0.74);
        tradeMessage2.setUserId("4");
        tradeMessage2.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage2.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessage2.setOriginatingCountry(CountryEnum.GB);
        tradeMessageDao.storeMessage(tradeMessage2);

        TradeMessage tradeMessage3 = new TradeMessage();
        tradeMessage3.setAmountBuy(150.0);
        tradeMessage3.setAmountSell(200.0);
        tradeMessage3.setRate(0.74);
        tradeMessage3.setUserId("4");
        tradeMessage3.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessage3.setCurrencyTo(CurrencyEnum.USD);
        tradeMessage3.setOriginatingCountry(CountryEnum.US);
        tradeMessageDao.storeMessage(tradeMessage3);


        volumeMessageList =  tradeMessageDao.getVolumes();
        assertEquals(2,volumeMessageList.size());
        assertEquals(CurrencyEnum.USD.name(),volumeMessageList.get(0).getId());
        assertEquals(200.0,volumeMessageList.get(0).getVolume());
        assertEquals(CurrencyEnum.GBP.name(),volumeMessageList.get(1).getId());
        assertEquals(400.0,volumeMessageList.get(1).getVolume());



    }
}