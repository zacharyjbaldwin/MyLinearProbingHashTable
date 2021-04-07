class LinearProbingHashTable<K, V>
{
    public static void main(String args[])
    {
	LinearProbingHashTable<Integer, String> test = new LinearProbingHashTable<Integer, String>();
	try
	{
	    test.insert(3, "dogs");
	    test.insert(3, "cats");
	    test.insert(3, "emus");
	    test.insert(19, "monkeys");
	    test.insert(1, "sheep");
	    test.print();
// rehash happens here
	    test.insert(11, "deer");
	} catch (Exception e)
	{
	}
	test.print();
	System.out.println(test.find(3));
	test.delete(3);
	test.print();
    }

    private Entry<K, V> table[];
    private int table_size = 10;
    private int entries = 0;

    private class Entry<K, V>
    {
	public K key;
	public V value;

	public Entry(K key, V value)
	{
	    this.key = key;
	    this.value = value;
	}
    }

    public LinearProbingHashTable()
    {
	table = new Entry[table_size];
    }

    private int realFind(K key, boolean empty) throws Exception
    {
// Empty flag returns first empty spot if true, if false only returns if key is there
	int origin = hash(key);
	int index = origin;
	while (true)
	{
	    try
	    {
			if (table[index].key == key && empty == false)
			{
				return index;
			}
	    } 
		catch (Exception e)
		{
	// This branch only comes up if the space is empty
			if (empty)
			{
				return index;
			}
	    }
	    index = (index + 1) % table_size;
	    if (index == origin)
	    {
			throw new Exception("Value not found...");
	    }
	}
    }

    public void insert(K key, V value) throws Exception
    {
	if (key == null)
	{
	    throw new Exception("Key must not be null");
	}
	int index = realFind(key, true);
	table[index] = new Entry(key, value);
	entries++;
	if (entries > table_size / 2)
	{
	    rehash();
	}
    }

    public V find(K key)
    {
	try
	{
	    int index = realFind(key, false);
	    return table[index].value;
	} catch (Exception e)
	{
	    return null;
	}
    }

    public boolean delete(K key)
    {
	try
	{
	    int index = realFind(key, false);
	    table[index] = null;
	    entries--;
	    return true;
	} catch (Exception e)
	{
	    return false;
	}
    }

    private void rehash() throws Exception
    {
	Entry<K, V> temporary_table[];
	temporary_table = new Entry[table_size];
	System.arraycopy(table, 0, temporary_table, 0, table.length);
	table_size = table_size * 2;
	entries = 0;
	table = new Entry[table_size];
	for (int i = 0; i < table_size / 2; i++)
	{
	    Entry<K, V> e = temporary_table[i];
	    if (e != null)
	    {
		insert(e.key, e.value);
	    }
	}
    }

    public int hash(K key)
    {
	int code = key.hashCode();
	return code % table_size;
    }

    public void print()
    {
	System.out.println("LinearProbingHashTable Print---");
	System.out.println("Size : " + table_size);
	for (int i = 0; i < table_size; i++)
	{
	    Entry<K, V> e = table[i];
	    if (e == null)
	    {
		System.out.println(i + " empty");
	    } else
	    {
		System.out.println(i + " " + e.key + " " + e.value);
	    }
	}
    }
}