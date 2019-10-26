import java.util.*

class Trie {
    private inner class TrieNode {
        val children = HashMap<Char, TrieNode>()
        var endOfWord: Boolean = false
    }

    private val rootNode: TrieNode = TrieNode()
    
    fun insert(word: String) {
        var current = rootNode
        
        for(i in 0..word.length-1) {
            current = current.children.computeIfAbsent(word[i]) {
                _ -> TrieNode()
            }
        }
        
        current.endOfWord = true
    }
    
    fun delete(word: String): Boolean {

        var current = rootNode
        
        if (word.isEmpty() || current.endOfWord || current.children.isEmpty())
        	return false
        
        val stack = Stack<TrieNode>()
        
        for (i in 0..word.length - 1) {
            if (!current.children.contains(word[i]))
            	return false
            
            stack.push(current)
            current = current.children[word[i]]!!
        }
        
        if (!current.endOfWord)
        	return false
        
        current.endOfWord = false
        
        if (current.children.isEmpty()) {
            var popped = stack.pop()
            
            while(stack.isNotEmpty() && popped.children.size == 1) {
                popped.children.clear()
                
                if (popped.endOfWord)
                   break
                
                popped = stack.pop()
            }
        }
       
        return true
    }
    
    fun getAllWords(): ArrayList<String> {
        val list = arrayListOf<String>()
        
        var current: TrieNode = rootNode

        if (!current.endOfWord)
	        words(current.children, "", list)
        
        return list
    }
    
    fun getWords(prefix: String): ArrayList<String> {
        val list = arrayListOf<String>()
        
        if (prefix.isNotEmpty()) {
            var current: TrieNode = rootNode
            
	        for (i in 0..prefix.length - 1) {
    	        if (!current.children.contains(prefix[i])) {
                    return list
            	}
            
	            current = current.children[prefix[i]]!!
    	    }
            
            if (current.endOfWord)
            	return arrayListOf<String>(prefix)
                
            words(current.children, prefix, list)
        }
        
        return list
    }
    
    private fun words(children: HashMap<Char, TrieNode>, prefix: String, words: ArrayList<String>) {
        for(child in children) {
            val nstr = prefix + child.key
            
            if (child.value.endOfWord)
               words.add(nstr)
               
            else if (child.value.children.isNotEmpty())
            	words(child.value.children, nstr, words)
        }
    }
    
    fun search(word: String): Boolean {
        
        if (word.isEmpty())
        	return false
       
        var current = rootNode
        
        for (i in 0..word.length - 1) {
            if (!current.children.contains(word[i])) {
                return false
            }
            
            current = current.children[word[i]]!!
        }
        
        return current.endOfWord
    }
    
    fun dump(): String {
        return _dump(rootNode.children)
    }
    
    private fun _dump(children: HashMap<Char, TrieNode>): String {
        if (children.isEmpty())
        	return ""
        
        var s = ""
        
        for (child in children) {
            s += child.key + _dump(child.value.children)
        }
        
        return s
    }
}


fun main() {
    val t = Trie()
    
    t.insert("box")
    t.insert("books")
    t.insert("bills")
    t.insert("maze")
    t.insert("made")
    t.insert("king")
    t.insert("kong")
	t.insert("kotlin")
    t.insert("cake")
    t.insert("cann")
    t.insert("can")
    t.insert("coke")
    t.insert("cane")
    t.insert("cook")
    t.insert("bird")
    t.insert("zigzag")
    
    println(t.dump())
    
    println(t.search("can"))
   	println(t.delete("can"))
    println(t.search("can"))
    println(t.search("cann"))
    
    println(t.getWords("co").toString())
    println(t.getWords("ma").toString())
    println(t.getWords("kotlin").toString())
    println(t.getAllWords().toString())
            
}