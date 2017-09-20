package com.flowingcode.vaadin.addons.errorwindow;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Component to visualize an error, caused by an exception, as a modal sub-window. <br> 
 * When in production mode it shows a code to report. <br>
 * When in debug mode it allows to visualize the stack trace of the error.
 * 
 * @author pbartolo
 *
 */
@SuppressWarnings("serial")
public class ErrorWindow extends Window {

    private static final Logger logger = LoggerFactory.getLogger(ErrorWindow.class);

    private static final String DEFAULT_CAPTION = "<b>An error has occurred</b>";

    private static final String DEFAULT_ERROR_LABEL_MESSAGE = "Please contact the system administrator for more information.";

    private VerticalLayout exceptionTraceLayout;

    private final Throwable cause;

    private final String errorMessage;

    private final String uuid;

    /**
     * Constructs an ErrorWindow component with default settings.
     */
    public ErrorWindow(final Throwable cause) {
        this(cause, null);
    }

    /**
     * Constructs an ErrorWindow with an specific error message.
     */
    public ErrorWindow(final Throwable cause, final String errorMessage) {
        super();
        assert cause != null;

        uuid = UUID.randomUUID().toString();
        this.cause = cause;
        this.errorMessage = errorMessage;
        initWindow();
    }

    public ErrorWindow open(final UI ui) {
        ui.addWindow(this);
        return this;
    }

    private void initWindow() {
        logger.error(String.format("Error occurred %s", uuid), cause);
        setWidth(800, Unit.PIXELS);
        setModal(true);
        addCloseShortcut(KeyCode.ESCAPE);
        setCaption(DEFAULT_CAPTION);
        setCaptionAsHtml(true);
        setContent(createMainLayout());
        setModal(true);
    }

    /**
     * Creates the main layout of the ErrorWindow.
     */
    private VerticalLayout createMainLayout() {
        final VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        final Label errorLabel = createErrorLabel();
        mainLayout.addComponent(errorLabel);
        mainLayout.setComponentAlignment(errorLabel, Alignment.TOP_LEFT);

        if (!isProductionMode()) {
            mainLayout.addComponent(createDetailsButtonLayout());
            mainLayout.addComponent(createExceptionTraceLayout());
        }

        final Button closeButton = new Button("Close", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                close();
            }
        });
        mainLayout.addComponent(closeButton);
        mainLayout.setComponentAlignment(closeButton, Alignment.TOP_RIGHT);

        return mainLayout;
    }

    private HorizontalLayout createDetailsButtonLayout() {
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        final Button errorDetailsButton = new Button("Show error detail", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                exceptionTraceLayout.setVisible(!exceptionTraceLayout.isVisible());
            }
        });
        errorDetailsButton.addStyleName("link small");
        errorDetailsButton.setIcon(FontAwesome.PLUS);
        buttonsLayout.addComponent(errorDetailsButton);
        buttonsLayout.setComponentAlignment(errorDetailsButton, Alignment.TOP_LEFT);
        return buttonsLayout;
    }

    private VerticalLayout createExceptionTraceLayout() {
        exceptionTraceLayout = new VerticalLayout();
        exceptionTraceLayout.addComponent(createStackTraceArea());
        exceptionTraceLayout.setVisible(false);
        return exceptionTraceLayout;
    }

    protected TextArea createStackTraceArea() {
        final TextArea area = new TextArea();
        area.setWordwrap(false);
        area.setWidth(100, Unit.PERCENTAGE);
        area.setRows(15);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintWriter pw = new PrintWriter(baos);
        if (cause instanceof MethodException) {
            cause.getCause().printStackTrace(pw);
        } else {
            cause.printStackTrace(pw);
        }
        pw.flush();
        area.setValue(baos.toString());
        return area;
    }

    protected Label createErrorLabel() {
        String label = errorMessage == null ? DEFAULT_ERROR_LABEL_MESSAGE : errorMessage;
        if (isProductionMode()) {
            label = label.concat(String.format("<br />Report the following code:<p><center>%s<center/></p>", uuid));
        }
        final Label errorLabel = new Label(label, ContentMode.HTML);
        errorLabel.setWidth(100, Unit.PERCENTAGE);
        return errorLabel;
    }

    /**
     * To know if the app is in production mode.
     * 
     * @return true if is production mode
     */
    private boolean isProductionMode() {
        return VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode();
    }

}
