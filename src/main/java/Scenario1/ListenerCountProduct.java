package Scenario1;

import com.griddynamics.jagger.engine.e1.Provider;
import com.griddynamics.jagger.engine.e1.collector.AvgMetricAggregatorProvider;
import com.griddynamics.jagger.engine.e1.collector.MaxMetricAggregatorProvider;
import com.griddynamics.jagger.engine.e1.collector.MetricDescription;
import com.griddynamics.jagger.engine.e1.collector.MinMetricAggregatorProvider;
import com.griddynamics.jagger.engine.e1.collector.invocation.InvocationInfo;
import com.griddynamics.jagger.engine.e1.collector.invocation.InvocationListener;
import com.griddynamics.jagger.engine.e1.services.ServicesAware;
import com.griddynamics.jagger.invoker.InvocationException;
import com.griddynamics.jagger.invoker.v2.JHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class ListenerCountProduct extends ServicesAware implements Provider<InvocationListener> {

    private static Logger log = LoggerFactory.getLogger(ListenerCountProduct.class);

    private final String metricName = "total-count-product";

    @Override
    protected void init() {
        getMetricService().createMetric(new MetricDescription(metricName)
                .displayName("The amount of products")
                .showSummary(true)
                .plotData(true)
                .addAggregator(new AvgMetricAggregatorProvider())
                .addAggregator(new MinMetricAggregatorProvider())
                .addAggregator(new MaxMetricAggregatorProvider())
        );
    }


    @Override
    public InvocationListener provide() {
        {
            return new InvocationListener() {
                @Override
                public void onStart(InvocationInfo invocationInfo) {
                }

                @Override
                public void onSuccess(InvocationInfo invocationInfo) {
                    if (invocationInfo.getResult() != null) {
                        JHttpResponse jHttpResponse = (JHttpResponse) invocationInfo.getResult();
                        String response = jHttpResponse.getBody().toString();
                        String result = response.substring(response.indexOf("<totalProductCount>") + 19, response.indexOf("</totalProductCount>"));
                        int count = Integer.parseInt(result);
                        log.info(result);
                        getMetricService().saveValue(metricName, count);
                    }
                }

                @Override
                public void onFail(InvocationInfo invocationInfo, InvocationException e) {
                }

                @Override
                public void onError(InvocationInfo invocationInfo, Throwable error) {
                }
            };
        }
    }
}
