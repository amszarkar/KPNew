package velociter.kumar.property.data;

public class Offer {
    String project_id,organization_id,title,valid_from,valid_to,terms_and_condition,offer_image;


    public Offer(String project_id, String organization_id, String title, String valid_from, String valid_to, String terms_and_condition, String offer_image) {
        this.project_id = project_id;
        this.organization_id = organization_id;
        this.title = title;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
        this.terms_and_condition = terms_and_condition;
        this.offer_image = offer_image;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public String getTerms_and_condition() {
        return terms_and_condition;
    }

    public void setTerms_and_condition(String terms_and_condition) {
        this.terms_and_condition = terms_and_condition;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }
}
