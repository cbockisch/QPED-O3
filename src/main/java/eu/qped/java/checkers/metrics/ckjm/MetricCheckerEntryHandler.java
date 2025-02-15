package eu.qped.java.checkers.metrics.ckjm;

import eu.qped.java.checkers.metrics.data.report.ClassMetricsEntry;
import eu.qped.java.checkers.metrics.data.report.ClassMetricsMessage;
import eu.qped.java.checkers.metrics.data.report.ClassMetricsMessageMulti;
import eu.qped.java.checkers.metrics.data.report.ClassMetricsMessageSingle;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.qped.java.checkers.metrics.ckjm.MetricCheckerEntryHandler.Metric.*;

/**
 * Class is used to handle the output generated by the checker/ckjm.
 * The output is saved in a List of {@link ClassMetricsEntry}s.
 *
 * @author Jannik Seus
 */
@Getter
public class MetricCheckerEntryHandler implements CkjmOutputHandler {

    private final List<ClassMetricsEntry> outputMetrics;

    public MetricCheckerEntryHandler() {
        this.outputMetrics = new ArrayList<>();
    }

    @Override
    public void handleClass(String className, ClassMetrics c) {

        List<ClassMetricsMessage> metricsForClass = new ArrayList<>();

        Map<String, Integer> metricValuesCC = getCCMapInternal(c);

        metricsForClass.add(new ClassMetricsMessageSingle(AMC, c.getAmc()));
        metricsForClass.add(new ClassMetricsMessageSingle(CA, c.getCa()));
        metricsForClass.add(new ClassMetricsMessageSingle(CAM, c.getCam()));
        metricsForClass.add(new ClassMetricsMessageSingle(CBM, c.getCbm()));
        metricsForClass.add(new ClassMetricsMessageSingle(CBO, c.getCbo()));
        metricsForClass.add(new ClassMetricsMessageMulti(CC, metricValuesCC));
        metricsForClass.add(new ClassMetricsMessageSingle(CE, c.getCe()));
        metricsForClass.add(new ClassMetricsMessageSingle(DAM, c.getDam()));
        metricsForClass.add(new ClassMetricsMessageSingle(DIT, c.getDit()));
        metricsForClass.add(new ClassMetricsMessageSingle(LCOM, c.getLcom()));
        metricsForClass.add(new ClassMetricsMessageSingle(LCOM3, c.getLcom3()));
        metricsForClass.add(new ClassMetricsMessageSingle(LOC, c.getLoc()));
        metricsForClass.add(new ClassMetricsMessageSingle(MOA, c.getMoa()));
        metricsForClass.add(new ClassMetricsMessageSingle(MFA, c.getMfa()));
        metricsForClass.add(new ClassMetricsMessageSingle(IC, c.getIc()));
        metricsForClass.add(new ClassMetricsMessageSingle(NOC, c.getNoc()));
        metricsForClass.add(new ClassMetricsMessageSingle(NPM, c.getNpm()));
        metricsForClass.add(new ClassMetricsMessageSingle(RFC, c.getRfc()));
        metricsForClass.add(new ClassMetricsMessageSingle(WMC, c.getWmc()));

        this.outputMetrics.add(new ClassMetricsEntry(className, metricsForClass));
    }

    /**
     * Retrieves the internally private Map ([method name, cc value]-pair) for Cyclomatic Complexity metric.
     *
     * @param classMetrics the given classMetrics
     * @return a map containing CC-values for this class' methods
     */
    private Map<String, Integer> getCCMapInternal(ClassMetrics classMetrics) {

        List<String> methodNames = classMetrics.getMethodNames();
        Map<String, Integer> metricValuesCC = new HashMap<>();
        for (String methodName : methodNames) {
            metricValuesCC.put(methodName, classMetrics.getCC(methodName));
        }
        return metricValuesCC;
    }

    /**
     * Metrics enum representing all possible class metrics for the design checker.
     *
     * @author Jannik Seus
     */
    public enum Metric {

        AMC("Average Method Complexity"),
        CA("Afferent Coupling"),
        CAM("Cohesion Among Methods Of Class"),
        CBM("Coupling Between Methods"),
        CBO("Coupling Between Object Classes"),
        CC("McCabe's Cyclomatic Complexity"),
        CE("Efferent Coupling"),
        DAM("Data Access Metric"),
        DIT("Depth Of Inheritance Tree"),
        IC("Inheritance Coupling"),
        LCOM("Lack Of Cohesion In Methods"),
        LCOM3("Henderson-Sellers' Lack Of Cohesion In Methods"),
        LOC("Lines Of Code"),
        MFA("Measure Of Functional Abstraction"),
        MOA("Measure Of Aggregation"),
        NOC("Number Of Children"),
        NPM("Number Of Public Methods For A Class"),
        RFC("Response For A Class"),
        WMC("Weighted Methods Per Class");

        private final String description;

        /**
         * Main constructor.
         * as well as min and max values for a specific metric. These values cannot be exceeded.
         *  @param description (long) description for an abbreviation of a given metric
         *
         */
        Metric(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
