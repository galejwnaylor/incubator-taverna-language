/**
 * 
 */
package uk.org.taverna.scufl2.api.core;

import javax.xml.bind.annotation.XmlTransient;

import uk.org.taverna.scufl2.api.reference.Reference;


/**
 * @author Alan R Williams
 *
 */
public abstract class ProcessorControlledStartCondition extends StartCondition {
	
	private Processor controllingProcessor;

	public Reference<Processor> getControllingProcessorReference() {
		return Reference.createReference(controllingProcessor);
	}

	public void setControllingProcessorReference(Reference<Processor> controllingProcessorReference) {
		controllingProcessor = controllingProcessorReference.resolve();
	}
	

	/**
	 * @return
	 */
	@XmlTransient
	public Processor getControllingProcessor() {
		return controllingProcessor;
	}

	/**
	 * @param controllingProcessor
	 */
	public void setControllingProcessor(Processor controllingProcessor) {
		this.controllingProcessor = controllingProcessor;
	}

}
