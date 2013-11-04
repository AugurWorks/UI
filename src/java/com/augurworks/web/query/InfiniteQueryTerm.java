package java.com.augurworks.web.query;

import java.util.Date;

public class InfiniteQueryTerm {
    private final String etext;
    private final String ftext;
    private final String entity;
    private final TimeConstraint timeConstraint;

    private InfiniteQueryTerm(String etext, String ftext, String entity, TimeConstraint timeConstraint) {
        this.etext = etext;
        this.ftext = ftext;
        this.entity = entity;
        this.timeConstraint = timeConstraint;
    }

    public String getQueryTerm() {
        StringBuilder sb = new StringBuilder("{");
        if (etext != null) {
            sb = sb.append("\"etext\":\"" + etext + "\",\n");
        }
        if (ftext != null) {
            sb = sb.append("\"ftext\":\"" + ftext + "\",\n");
        }
        sb = sb.append(timeConstraint.formatForQuery());
        sb = sb.append(",");
        if (entity != null) {
            sb = sb.append("\"entity\":\"" + entity + "\",\n");
        }
        // remove last comma
        sb = sb.deleteCharAt(sb.length() - 1);
        sb = sb.append("}");
        return sb.toString();
    }

    public static Builder builder() {
    	return new Builder();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((etext == null) ? 0 : etext.hashCode());
        result = prime * result + ((ftext == null) ? 0 : ftext.hashCode());
        result = prime * result
                + ((timeConstraint == null) ? 0 : timeConstraint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        InfiniteQueryTerm other = (InfiniteQueryTerm) obj;
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        if (etext == null) {
            if (other.etext != null) {
                return false;
            }
        } else if (!etext.equals(other.etext)) {
            return false;
        }
        if (ftext == null) {
            if (other.ftext != null) {
                return false;
            }
        } else if (!ftext.equals(other.ftext)) {
            return false;
        }
        if (timeConstraint == null) {
            if (other.timeConstraint != null) {
                return false;
            }
        } else if (!timeConstraint.equals(other.timeConstraint)) {
            return false;
        }
        return true;
    }

    public static class Builder {
        private String etext = null;
        private String ftext = null;
        private String entity = null;
        private Date startTime = null;
        private Date endTime = null;

        public Builder() {
        }

        public Builder withEtext(String etext) {
            this.etext = etext;
            return this;
        }

        public Builder withFtext(String ftext) {
            this.ftext = ftext;
            return this;
        }

        public Builder withEntity(String entity) {
            this.entity = entity;
            return this;
        }

        public Builder withStartTime(String startTime) {
            this.startTime = DateParser.tryAllParsers(startTime);
            return this;
        }

        public Builder withEndTime(String endTime) {
            this.endTime = DateParser.tryAllParsers(endTime);
            return this;
        }

        private boolean isEmpty() {
            return (etext == null) && (ftext == null) && (entity == null)
                    && (startTime == null) && (endTime == null);
        }

        public InfiniteQueryTerm build() {
            if (isEmpty()) {
                throw new IllegalArgumentException("Cannot construct empty query");
            }
            return new InfiniteQueryTerm(etext, ftext, entity,
                    new TimeConstraint(startTime, endTime));
        }
    }
}