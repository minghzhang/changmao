package com.louis.translate;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @date : 2021/9/24
 */
public class GoogleTranslateTest {

    @Test
    public void translateText() throws Exception {
        GoogleTranslate instance = GoogleTranslate.getInstance();
        String value = instance.translateText("Preface two\n" +
                "I met the author Wang Xindong for the first time in the first company he joined after graduating from university. He had just graduated and joined the company at that time. More than ten years have passed. During this period, we worked together in three companies and worked together for seven years. For eight years, we will continue to communicate online and offline from time to time until now.\n" +
                "After a long period of precipitation, Wang Xindong has gradually grown from a newcomer to the workplace to an architect responsible for tens of billions of calls. After summarizing and thinking about the experience during this period, the author recorded the content that is worth learning and sharing, and put it together in a book, hoping to help and think for students who are also in the field of computer development.\n" +
                "Flipping through the chapters in the book often makes me feel like deja vu. Most systems, from complex distributed clusters, down to simpler microservices, and even allinone systems. In addition to business logic, more than 80% of our energy is in consideration of the robustness, scalability and solution of the system. The abnormal situation. With the unfolding of each chapter, this book lists us the necessary conditions for a stable, maintainable and robust system, as well as the response strategies when a catastrophic failure occurs, and provides us with a whole set of ideas for designing the system. .\n" +
                "I often see development students who have been working for two or three years saying that they don’t know anything except basic CURD, and have no idea how to design and iterate the system. If you have the same doubts, then this book should be quite suitable for you. By reading this book, we can know: if we are responsible for a system, in addition to the CURD related to the business foundation, what needs to be understood and what needs to be considered. I believe that after reading this book, students who have just stepped into the development field will be able to see the system architecture and programming methods that should be paid more attention to besides CURD. Ability to quickly advance from writing business code to a higher stage. Of course, students who have already started can also use it as a reference book. Compared with the classic theories, the knowledge points explained in this book are more local, and most of the knowledge points involved in the book are abstracted and refined from the real production environment. Come. The knowledge points of this book can provide you with some reference and inspiration, which can let you find inspiration for solving problems. Ctrip Financial Technology Director\n" +
                "Li Yong\n", "auto", "zh_cn");
        System.out.println(value);
    }
}