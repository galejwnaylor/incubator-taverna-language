package uk.org.taverna.scufl2.api.configurations;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PropertyResource implements PropertyObject {
	private URI resourceURI;
	private URI typeURI;
	private final Map<URI, Set<PropertyObject>> properties = new LazyMap<URI, Set<PropertyObject>>() {
		private static final long serialVersionUID = 1L;

		@Override
		public Set<PropertyObject> getDefault(URI key) {
			return new LinkedHashSet<PropertyObject>();
		}
	};

	public PropertyResource() {
	}

	public PropertyResource(URI resourceURI) {
		setResourceURI(resourceURI);
	}

	public void addProperty(URI predicate, PropertyObject object) {
		getProperties().get(predicate).add(object);
	}

	public void addProperty(URI predicate, String value) {
		addProperty(predicate, new PropertyLiteral(value));
	}

	public PropertyResource addPropertyResource(URI predicate, URI typeURI) {
		PropertyResource resource = new PropertyResource();
		resource.setTypeURI(typeURI);
		addProperty(predicate, resource);
		return resource;
	}

	public void addPropertyURI(URI predicate, URI resourceURI) {
		addProperty(predicate, new PropertyResource(resourceURI));
	}

	public final Map<URI, Set<PropertyObject>> getProperties() {
		return properties;
	}

	public Set<PropertyLiteral> getPropertiesAsLiterals(URI predicate) {
		return getPropertiesOfType(predicate, PropertyLiteral.class);
	}

	public Set<PropertyResource> getPropertiesAsResources(URI predicate) {
		return getPropertiesOfType(predicate, PropertyResource.class);
	}

	public Set<URI> getPropertiesAsResourceURIs(URI predicate) {
		Set<URI> uris = new HashSet<URI>();
		for (PropertyResource resource : getPropertiesAsResources(predicate)) {
			URI uri = resource.getResourceURI();
			if (uri == null) {
				throw new IllegalStateException(
						"Resource property without URI for " + predicate
						+ " in " + this + ": " + resource);
			}
			uris.add(uri);
		}
		return uris;
	}

	public Set<String> getPropertiesAsStrings(URI predicate) {
		Set<String> strings = new HashSet<String>();
		for (PropertyLiteral literal : getPropertiesAsLiterals(predicate)) {
			strings.add(literal.getLiteralValue());
		}
		return strings;
	}

	protected <PropertyType extends PropertyObject> Set<PropertyType> getPropertiesOfType(
			URI predicate, Class<PropertyType> propertyType) {
		Set<PropertyType> properties = new HashSet<PropertyType>();
		for (PropertyObject obj : getProperties().get(predicate)) {
			if (!propertyType.isInstance(obj)) {
				throw new IllegalStateException("Not a " + propertyType + ": "
						+ predicate + " in " + this);
			}
			properties.add(propertyType.cast(obj));
		}
		return properties;
	}

	public PropertyObject getProperty(URI predicate)
	throws PropertyNotFoundException {
		PropertyObject foundProperty = null;
		// Could have checked set's size() - but it's
		for (PropertyObject obj : getProperties().get(predicate)) {
			if (foundProperty != null) {
				throw new IllegalStateException("More than one property "
						+ predicate + " exists in " + this);
			}
			foundProperty = obj;
		}
		if (foundProperty == null) {
			throw new PropertyNotFoundException("Can't find " + predicate
					+ " in " + this);
		}
		return foundProperty;
	}

	public URI getPropertyAsResourceURI(URI predicate)
	throws PropertyNotFoundException {
		PropertyResource propertyResource = getPropertyOfType(predicate,
				PropertyResource.class);
		URI uri = propertyResource.getResourceURI();
		if (uri == null) {
			throw new IllegalStateException(
					"Resource property without URI for " + predicate
					+ " in " + this + ": " + propertyResource);
		}
		return uri;
	}

	public String getPropertyAsString(URI predicate)
	throws PropertyNotFoundException {
		PropertyLiteral propertyLiteral = getPropertyOfType(predicate,
				PropertyLiteral.class);
		return propertyLiteral.getLiteralValue();
	}

	protected <PropertyType extends PropertyObject> PropertyType getPropertyOfType(
			URI predicate, Class<PropertyType> propertyType)
	throws PropertyNotFoundException {
		PropertyObject propObj = getProperty(predicate);
		if (!propertyType.isInstance(propObj)) {
			throw new IllegalStateException("Not a " + propertyType + ": "
					+ predicate + " in " + this);
		}
		return propertyType.cast(propObj);
	}

	public final URI getResourceURI() {
		return resourceURI;
	}

	public final URI getTypeURI() {
		return typeURI;
	}

	public final void setProperties(Map<URI, Set<PropertyObject>> properties) {
		this.properties.clear();
		this.properties.putAll(properties);
	}

	public final void setResourceURI(URI resourceURI) {
		this.resourceURI = resourceURI;
	}

	public final void setTypeURI(URI typeURI) {
		this.typeURI = typeURI;
	}

}
