package com.thebuyback.eve.web.rest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.thebuyback.eve.App;
import com.thebuyback.eve.domain.MarketOffer;
import com.thebuyback.eve.domain.enumeration.MarketOfferCategory;
import com.thebuyback.eve.domain.enumeration.MarketOfferType;
import com.thebuyback.eve.repository.MarketOfferRepository;
import com.thebuyback.eve.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the MarketOfferResource REST controller.
 *
 * @see MarketOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ContextConfiguration(initializers = EnvironmentTestConfiguration.class)
public class MarketOfferResourceIntTest {

    private static final String DEFAULT_ISSUER = "AAAAAAAAAA";
    private static final String UPDATED_ISSUER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRY_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final MarketOfferType DEFAULT_TYPE = MarketOfferType.SELL;
    private static final MarketOfferType UPDATED_TYPE = MarketOfferType.BUY;

    private static final MarketOfferCategory DEFAULT_CATEGORY = MarketOfferCategory.NONE;
    private static final MarketOfferCategory UPDATED_CATEGORY = MarketOfferCategory.NONE;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_RECURRING = false;
    private static final Boolean UPDATED_IS_RECURRING = true;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private MarketOfferRepository marketOfferRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMarketOfferMockMvc;

    private MarketOffer marketOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarketOfferResource marketOfferResource = new MarketOfferResource(marketOfferRepository);
        this.restMarketOfferMockMvc = MockMvcBuilders.standaloneSetup(marketOfferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarketOffer createEntity() {
        MarketOffer marketOffer = new MarketOffer()
            .issuer(DEFAULT_ISSUER)
            .created(DEFAULT_CREATED)
            .expiry(DEFAULT_EXPIRY)
            .expiryUpdated(DEFAULT_EXPIRY_UPDATED)
            .type(DEFAULT_TYPE)
            .category(DEFAULT_CATEGORY)
            .location(DEFAULT_LOCATION)
            .isRecurring(DEFAULT_IS_RECURRING)
            .text(DEFAULT_TEXT);
        return marketOffer;
    }

    @Before
    public void initTest() {
        marketOfferRepository.deleteAll();
        marketOffer = createEntity();
    }

    @Test
    public void createMarketOffer() throws Exception {
        int databaseSizeBeforeCreate = marketOfferRepository.findAll().size();

        // Create the MarketOffer
        restMarketOfferMockMvc.perform(post("/api/market-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOffer)))
            .andExpect(status().isCreated());

        // Validate the MarketOffer in the database
        List<MarketOffer> marketOfferList = marketOfferRepository.findAll();
        assertThat(marketOfferList).hasSize(databaseSizeBeforeCreate + 1);
        MarketOffer testMarketOffer = marketOfferList.get(marketOfferList.size() - 1);
        assertThat(testMarketOffer.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMarketOffer.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMarketOffer.isIsRecurring()).isEqualTo(DEFAULT_IS_RECURRING);
        assertThat(testMarketOffer.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    public void createMarketOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketOfferRepository.findAll().size();

        // Create the MarketOffer with an existing ID
        marketOffer.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketOfferMockMvc.perform(post("/api/market-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOffer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketOffer> marketOfferList = marketOfferRepository.findAll();
        assertThat(marketOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMarketOffers() throws Exception {
        // Initialize the database
        marketOfferRepository.save(marketOffer);

        // Get all the marketOfferList
        restMarketOfferMockMvc.perform(get("/api/market-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketOffer.getId())))
            .andExpect(jsonPath("$.[*].issuer").value(hasItem(DEFAULT_ISSUER.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].expiry").value(hasItem(DEFAULT_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].expiryUpdated").value(hasItem(DEFAULT_EXPIRY_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].isRecurring").value(hasItem(DEFAULT_IS_RECURRING.booleanValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    public void getMarketOffer() throws Exception {
        // Initialize the database
        marketOfferRepository.save(marketOffer);

        // Get the marketOffer
        restMarketOfferMockMvc.perform(get("/api/market-offers/{id}", marketOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketOffer.getId()))
            .andExpect(jsonPath("$.issuer").value(DEFAULT_ISSUER.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.expiry").value(DEFAULT_EXPIRY.toString()))
            .andExpect(jsonPath("$.expiryUpdated").value(DEFAULT_EXPIRY_UPDATED.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.isRecurring").value(DEFAULT_IS_RECURRING.booleanValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    public void getNonExistingMarketOffer() throws Exception {
        // Get the marketOffer
        restMarketOfferMockMvc.perform(get("/api/market-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Ignore
    @Test
    public void updateMarketOffer() throws Exception {
        // Initialize the database
        marketOfferRepository.save(marketOffer);
        int databaseSizeBeforeUpdate = marketOfferRepository.findAll().size();

        // Update the marketOffer
        MarketOffer updatedMarketOffer = marketOfferRepository.findOne(marketOffer.getId());
        updatedMarketOffer
            .issuer(UPDATED_ISSUER)
            .created(UPDATED_CREATED)
            .expiry(UPDATED_EXPIRY)
            .expiryUpdated(UPDATED_EXPIRY_UPDATED)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .location(UPDATED_LOCATION)
            .isRecurring(UPDATED_IS_RECURRING)
            .text(UPDATED_TEXT);

        restMarketOfferMockMvc.perform(put("/api/market-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketOffer)))
            .andExpect(status().isOk());

        // Validate the MarketOffer in the database
        List<MarketOffer> marketOfferList = marketOfferRepository.findAll();
        assertThat(marketOfferList).hasSize(databaseSizeBeforeUpdate);
        MarketOffer testMarketOffer = marketOfferList.get(marketOfferList.size() - 1);
        assertThat(testMarketOffer.getIssuer()).isEqualTo(UPDATED_ISSUER);
        assertThat(testMarketOffer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testMarketOffer.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testMarketOffer.getExpiryUpdated()).isEqualTo(UPDATED_EXPIRY_UPDATED);
        assertThat(testMarketOffer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMarketOffer.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMarketOffer.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMarketOffer.isIsRecurring()).isEqualTo(UPDATED_IS_RECURRING);
        assertThat(testMarketOffer.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    public void updateNonExistingMarketOffer() throws Exception {
        int databaseSizeBeforeUpdate = marketOfferRepository.findAll().size();

        // Create the MarketOffer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketOfferMockMvc.perform(put("/api/market-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketOffer)))
            .andExpect(status().isCreated());

        // Validate the MarketOffer in the database
        List<MarketOffer> marketOfferList = marketOfferRepository.findAll();
        assertThat(marketOfferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Ignore
    @Test
    public void deleteMarketOffer() throws Exception {
        // Initialize the database
        marketOfferRepository.save(marketOffer);
        int databaseSizeBeforeDelete = marketOfferRepository.findAll().size();

        // Get the marketOffer
        restMarketOfferMockMvc.perform(delete("/api/market-offers/{id}", marketOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketOffer> marketOfferList = marketOfferRepository.findAll();
        assertThat(marketOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketOffer.class);
        MarketOffer marketOffer1 = new MarketOffer();
        marketOffer1.setId("id1");
        MarketOffer marketOffer2 = new MarketOffer();
        marketOffer2.setId(marketOffer1.getId());
        assertThat(marketOffer1).isEqualTo(marketOffer2);
        marketOffer2.setId("id2");
        assertThat(marketOffer1).isNotEqualTo(marketOffer2);
        marketOffer1.setId(null);
        assertThat(marketOffer1).isNotEqualTo(marketOffer2);
    }
}
