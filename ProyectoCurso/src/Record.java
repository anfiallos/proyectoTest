import java.util.Comparator;
class Record {

	    private String s;
	    public String getS() {
			return s;
		}



		public void setS(String s) {
			this.s = s;
		}



		public Double getD() {
			return d;
		}



		public void setD(Double d) {
			this.d = d;
		}

		private Double d;

	    public Record(String name, String number) {
	        this.s = name;
	        this.d = Double.valueOf(number);
	    }



		@Override
	    public String toString() {
	        return s + " " + d;
	    }

	    public int compareTo(Field field, Record record) {
	        switch (field) {
	            case S: return this.s.compareTo(record.s);
	            case D: return this.d.compareTo(record.d);
	            default: throw new IllegalArgumentException(
	                "Unable to sort Records by " + field.getType());
	        }
	    }
	}

	enum Sort { ASCENDING, DESCENDING; }

	enum Field {

	    S(String.class), D(Double.class);

	    private Class type;

	    Field(Class<? extends Comparable> type) {
	        this.type = type;
	    }

	    public Class getType() {
	        return type;
	    }
	}

	class RecordComparator implements Comparator<Record> {

	    private Field field;
	    private Sort sort;

	    public RecordComparator(Sort sort, Field field) {
	        this.sort = sort;
	        this.field = field;
	    }

	    @Override
	    public final int compare(Record a, Record b) {
	        int result = a.compareTo(field, b);
	        if (sort == Sort.ASCENDING) return result;
	        else return -result;
	    }
	}
