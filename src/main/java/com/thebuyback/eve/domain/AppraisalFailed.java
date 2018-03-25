package com.thebuyback.eve.domain;

import java.io.IOException;
import java.io.InputStream;

import com.mashape.unirest.request.body.Body;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppraisalFailed extends Exception {

    private static final long serialVersionUID = 2781884538931652756L;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final String TEMPLATE = "Request to %s failed: Status '%s', Body %s";
    private final String url;
    private final Body body;
    private String statusText;

    public AppraisalFailed(final String url, final Body body) {
        this.url = url;
        this.body = body;
    }

    public AppraisalFailed(final String url, final Body body, final String statusText) {
        this.url = url;
        this.body = body;
        this.statusText = statusText;
    }

    @Override
    public String getMessage() {
        String bodyText = "";
        try {
            bodyText = IOUtils.toString(body.getEntity().getContent(), "UTF-8").replace("\n", ";");
        } catch (IOException e) {
            LOG.error("Failed to read body from request.", e);
        }
        return String.format(TEMPLATE, url, bodyText, statusText);
    }
}
