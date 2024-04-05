package unittest;


import com.bismark.test.com.bismark.Student;
import com.bismark.test.com.bismark.StudentRepository;
import com.bismark.test.com.bismark.StudentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class StudentTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testCreateStudent() {
        Student std1 = new Student(1L, "Bolan", "bolan@gmail.com");

        when(studentRepository.save(std1)).thenReturn(std1);

        Student savedStd = studentService.addStudent(std1);

        Mockito.verify(studentRepository, Mockito.times(1)).save(std1);

        assertEquals(std1, savedStd);
    }

    @Test
    void beforeAll(){
        assertTrue(true);
    }
}
