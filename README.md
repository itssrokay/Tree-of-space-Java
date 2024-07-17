# Tree-of-space-Java

Question:
Locking the tree of space

You have a world map represented as an M-Ary tree (sample tree below).

You need to define three operations on it:

- lock(X, uid)
- unlock(X, uid)
- upgradeLock(x, uid)

where X is the name of a node in the tree (that would be unique) and uid is the user who is performing the operation.

Here are the definitions for the Operations:

**Lock(X, uid)**

Lock takes exclusive access to the subtree rooted at X. It is formally defined like this:
Once lock(X, uid) succeeds, then:
- lock(A, anyUserId) should fail (returns false), where A is a descendant of X,
- lock(B, anyUserId) should fail (returns false), where X is a descendant of B
- Lock operation cannot be performed on a node that is already locked i.e. lock(X, anyUserId) should fail (returns false).

**Unlock(X, uid)**

Unlock reverts what was done by the Lock operation. It can only be called on the same node on which user uid had called a Lock on before. Returns true if it is successful.

**UpgradeLock(X, uid)**

It helps the user uid upgrade their lock to an ancestor node. It is only possible if the node X already has locked descendants and all of them are only locked by the same user uid. Upgrade should fail if there is any node which is a descendant of X that is locked by a different user. Successful Upgrade will 'Lock' the node. UpgradeLock call shouldn't violate the consistency model that Lock/Unlock function requires.

**Notes**

1) The number of nodes in the tree N is very large. So, optimize the time complexity for the above algorithms.
2) The below section contains the input format.

- The first line contains the number of Nodes in the tree (N).
- The second line contains the number of children per node (value m in m-ary Tree).
- The third line contains the number of queries (Q).
- The next N lines contain the NodeName (string) in the m-Ary Tree.
- The next Q lines contain queries which are in format: OperationType NodeName UserId
  - OperationType → 1 for Lock, 2 for unlock, 3 for upgradeLock
  - NodeName → Name of any node (unique) in m-Ary Tree.
  - UserId → Integer value representing a unique user.

**Example input:**

7

2

3

World

Asia

Africa

China

India

SouthAfrica

Egypt

1 China 9

2 India 9

3 Asia 9

With the above input you represent a 2-ary tree with 7 nodes as follows:

```
                      World

                  /             \

              Asia              Africa

            /      \          /          \ 

        China     India    SouthAfrica    Egypt
```

**Additional Notes:**

1) Here ‘1 China 3’ indicates the following ‘OperationType NodeName UserId’
2) The tree is always fully balanced.
3) Constraints on the inputs are as follows:
        - 1 < N < 5 * 10^5
        - 1 < m < 30
        - 1 < Q < 5 * 10^5
        - 1 < length of NodeName < 20
4) Optimize the time complexity:
  - Lock  - O(log_m(N))
  - Unlock - O(log_m(N))
  - UpgradeLock - O(numberOfLockedNodes * log_m(N))
5) Lock operation on already locked node should fail.
6) Once UpgradeLock(X, uid) succeeds on X, it is equivalent to X being locked by uid. So, Lock(A/B, any user) should fail as per the definition of Lock and Unlock(X, uid) should also work.
7) Upgrade lock operation on a node having no locked descendants should fail and upgrade lock on an already locked node should also fail.
